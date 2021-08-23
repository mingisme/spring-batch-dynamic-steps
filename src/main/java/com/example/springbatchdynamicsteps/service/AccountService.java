package com.example.springbatchdynamicsteps.service;

import com.example.springbatchdynamicsteps.repository.AccountRepository;
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

import java.util.HashMap;
import java.util.Map;

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

    @SneakyThrows
    public void resetAccounts() {

        TaskletStep cleanAccountStep = stepBuilderFactory.get("cleanAccount")
                .tasklet(cleanAccountTasklet).build();

        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
        listener.setKeys(new String[]{"step"});

        TaskletStep initAccountStep = stepBuilderFactory.get("initAccount")
                .tasklet(initAccountTasklet).listener(listener).build();

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


}
