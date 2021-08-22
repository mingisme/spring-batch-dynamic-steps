package com.example.springbatchdynamicsteps;

import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.batch.configuration.TaskBatchExecutionListenerBeanPostProcessor;
import org.springframework.cloud.task.batch.configuration.TaskBatchExecutionListenerFactoryBean;
import org.springframework.cloud.task.batch.listener.TaskBatchExecutionListener;
import org.springframework.cloud.task.configuration.TaskConfigurer;
import org.springframework.cloud.task.configuration.TaskProperties;
import org.springframework.cloud.task.repository.TaskExplorer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnBean({ Job.class })
@ConditionalOnProperty(
        name = { "spring.cloud.task.batch.listener.enable",
                "spring.cloud.task.batch.listener.enabled" },
        havingValue = "true", matchIfMissing = true)
public class TaskBatchAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TaskBatchExecutionListenerBeanPostProcessor batchTaskExecutionListenerBeanPostProcessor() {
        return new TaskBatchExecutionListenerBeanPostProcessor();
    }

    /**
     * Auto configuration for {@link TaskBatchExecutionListener}.
     */
    @Configuration
    @ConditionalOnMissingBean(name = "taskBatchExecutionListener")
    @EnableConfigurationProperties(TaskProperties.class)
    public static class TaskBatchExecutionListenerAutoconfiguration {

        @Autowired
        private ApplicationContext context;

        @Autowired
        private TaskProperties taskProperties;

        @Bean
        public TaskBatchExecutionListenerFactoryBean taskBatchExecutionListener(
                TaskExplorer taskExplorer) {
            TaskConfigurer taskConfigurer = null;
            if (!this.context.getBeansOfType(TaskConfigurer.class).isEmpty()) {
                taskConfigurer = this.context.getBean(TaskConfigurer.class);
            }
            if (taskConfigurer != null && taskConfigurer.getTaskDataSource() != null) {
                return new TaskBatchExecutionListenerFactoryBean(
                        taskConfigurer.getTaskDataSource(), taskExplorer,
                        this.taskProperties.getTablePrefix());
            }
            else {
                return new TaskBatchExecutionListenerFactoryBean(null, taskExplorer,
                        this.taskProperties.getTablePrefix());
            }
        }

    }

}

