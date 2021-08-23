package com.example.springbatchdynamicsteps;

import lombok.SneakyThrows;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
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

	@Autowired
	private JobRepository jobRepository;

	@SneakyThrows
	@Bean("asyncJobLauncher")
	@Primary
	public JobLauncher jobLauncher() {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}

}
