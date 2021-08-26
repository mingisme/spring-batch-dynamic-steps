package com.example.springbatchdynamicsteps.service.task;


import com.example.springbatchdynamicsteps.model.ResourceLock;
import com.example.springbatchdynamicsteps.repository.ResourceLockRepository;
import com.example.springbatchdynamicsteps.service.task.context.Transfer;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Arrays;
import java.util.List;

public class AccountLockTasklet implements Tasklet {

   private final ResourceLockRepository resourceLockRepository;
   private final Transfer transfer;

   public AccountLockTasklet(ResourceLockRepository resourceLockRepository, Transfer transfer) {
      this.resourceLockRepository = resourceLockRepository;
      this.transfer = transfer;
   }

   @Override
   public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

      String from = transfer.getFrom();
      String to = transfer.getTo();

      List<String> accounts = Arrays.asList(from,to);
      accounts.sort((a, b)->a.compareTo(b));

      for(String account : accounts) {
         ResourceLock resourceLock = new ResourceLock();
         resourceLock.setResourceId(account);
         resourceLock.setCorrelatedId("");

      }

      return RepeatStatus.FINISHED;
   }
}
