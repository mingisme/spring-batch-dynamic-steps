package com.example.springbatchdynamicsteps.service;

import com.example.springbatchdynamicsteps.model.Account;
import com.example.springbatchdynamicsteps.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class InitAccountTasklet implements Tasklet {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Object deleteAllValue = contribution.getStepExecution().getJobExecution().getExecutionContext().get("deleteAll");
        log.info("deleteAll value: {}", deleteAllValue);

        for (int i = 1; i < 11; i++) {
            Account account = new Account();
            account.setAccountNumber("acc" + i);
            account.setBalance(BigDecimal.valueOf(1000));
            accountRepository.save(account);
        }

        return RepeatStatus.FINISHED;
    }
}
