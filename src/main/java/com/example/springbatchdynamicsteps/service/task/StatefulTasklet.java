package com.example.springbatchdynamicsteps.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class StatefulTasklet implements Tasklet {

    private final int threshhold;

    public StatefulTasklet(int threshhold) {
        this.threshhold = threshhold;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("threshhold value is {}", threshhold);
        if(threshhold<10){
            throw new RuntimeException("error");
        }

        return RepeatStatus.FINISHED;
    }
}
