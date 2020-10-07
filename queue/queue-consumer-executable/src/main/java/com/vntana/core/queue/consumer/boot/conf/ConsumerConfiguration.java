package com.vntana.core.queue.consumer.boot.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/7/20
 * Time: 2:56 PM
 */
@Configuration
public class ConsumerConfiguration {

    @Bean(name = "notificationSenderExecutor")
    public ThreadPoolTaskExecutor executor() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(4);
        threadPoolTaskExecutor.setCorePoolSize(64);
        threadPoolTaskExecutor.setQueueCapacity(1024);
        return threadPoolTaskExecutor;
    }
}
