package com.example.springbatchdynamicsteps.service;

import com.example.springbatchdynamicsteps.repository.AccountRepository;
import com.example.springbatchdynamicsteps.service.task.CleanAccountTasklet;
import com.example.springbatchdynamicsteps.service.task.InitAccountTasklet;
import com.example.springbatchdynamicsteps.service.task.LongTimeTasklet;
import com.example.springbatchdynamicsteps.service.task.StatefulTasklet;
import com.example.springbatchdynamicsteps.service.task.context.Transfer;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.batch.listener.TaskBatchExecutionListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AccountService {

    @Autowired(required = false)
    private TaskBatchExecutionListener taskBatchExecutionListener;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private CleanAccountTasklet cleanAccountTasklet;

    @Autowired
    private InitAccountTasklet initAccountTasklet;

    @Autowired
    private LongTimeTasklet longTimeTasklet;

    //test build job and steps dynamically
    @SneakyThrows
    public void resetAccounts() {

        //test promotion
        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
        listener.setKeys(new String[]{"step", "foo", "map"});

        TaskletStep cleanAccountStep = stepBuilderFactory.get("cleanAccount")
                .tasklet(cleanAccountTasklet).listener(listener).build();

        TaskletStep initAccountStep = stepBuilderFactory.get("initAccount")
                .tasklet(initAccountTasklet).listener(listener).build();

        //test long time task and async launcher
        TaskletStep longTimeStep = stepBuilderFactory.get("longTime")
                .tasklet(longTimeTasklet).build();

        Job resetAccountJob = jobBuilderFactory.get("resetAccounts")
                .listener(taskBatchExecutionListener)
                .start(cleanAccountStep)
                .next(initAccountStep)
                .next(longTimeStep).build();

        Map<String, JobParameter> parameterMap = new HashMap<>();
        parameterMap.put("jobId", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(parameterMap);
        jobLauncher.run(resetAccountJob, jobParameters);

    }

    @SneakyThrows
    public void threshHold(){
        TaskletStep step = stepBuilderFactory.get("stateFull")
                .tasklet(new StatefulTasklet(1)).build();

        Job job = jobBuilderFactory.get("threadHold")
                .start(step)
                .build();
        Map<String, JobParameter> parameterMap = new HashMap<>();
        parameterMap.put("jobId", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(parameterMap);
        jobLauncher.run(job, jobParameters);
    }

    public void transfer() {

        List<Transfer> tansfers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int from = new Random(13).nextInt(9) + 1;
            int to = from;
            for (int j = 0; j < 3; j++) {
                to = new Random(13).nextInt(9) + 1;
                if (from != to) {
                    break;
                }
            }
            if (from == to) {
                continue;
            }
            double amount = new Random(13).nextDouble() * 100;
            tansfers.add(new Transfer("acc" + from, "acc" + to, new BigDecimal(amount)));
        }


    }


}
