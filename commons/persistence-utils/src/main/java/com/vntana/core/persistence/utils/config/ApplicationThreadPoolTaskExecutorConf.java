package com.vntana.core.persistence.utils.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created by Geras Ghulyan.
 * Date: 10/28/19
 * Time: 5:56 PM
 */
@Configuration
public class ApplicationThreadPoolTaskExecutorConf {

    @Value("${executor.application.thread.core}")
    private int corePoolSize;

    @Value("${executor.application.thread.max}")
    private int maxPoolSize;

    @Value("${executor.application.thread.pool.size}")
    private int taskSchedulerThreadPoolSize;

    @Bean(name = "applicationThreadPoolTaskExecutor")
    public Executor executor() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(taskSchedulerThreadPoolSize);
        return threadPoolTaskExecutor;
    }
}
