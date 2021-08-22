package com.example.springbatchdynamicsteps;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTask
@EnableJpaRepositories
@EnableTransactionManagement
@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchDynamicStepsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDynamicStepsApplication.class, args);
	}

//	@Bean
//	public Job defaultJob(JobRepository jobRepository){
//		return new Job() {
//			@Override
//			public String getName() {
//				return "place-holder";
//			}
//
//			@Override
//			public boolean isRestartable() {
//				return false;
//			}
//
//			@Override
//			public void execute(JobExecution execution) {
//
//			}
//
//			@Override
//			public JobParametersIncrementer getJobParametersIncrementer() {
//				return null;
//			}
//
//			@Override
//			public JobParametersValidator getJobParametersValidator() {
//				return new DefaultJobParametersValidator();
//			}
//		};
//	}
}
