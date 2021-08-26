package com.example.springbatchdynamicsteps.service.task;

import com.example.springbatchdynamicsteps.repository.AccountRepository;
import com.example.springbatchdynamicsteps.service.task.context.Foo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CleanAccountTasklet implements Tasklet {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        //Test map context key/value
        Map<String,Object> map = new HashMap<>();
        map.put("A","X");
        map.put("B",1);
        contribution.getStepExecution().getExecutionContext().put("map", map);
        //Test json context key/value
        Foo foo = new Foo("Hi", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        contribution.getStepExecution().getExecutionContext().put("foo", objectMapper.writeValueAsString(foo));
        contribution.getStepExecution().getExecutionContext().put("step","cleanAccount");
        contribution.getStepExecution().getJobExecution().getExecutionContext().put("deleteAll","YES");
        accountRepository.deleteAll();

        return RepeatStatus.FINISHED;
    }
}
