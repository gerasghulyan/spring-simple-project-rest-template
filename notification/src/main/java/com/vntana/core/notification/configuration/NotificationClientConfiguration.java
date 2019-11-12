package com.vntana.core.notification.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 5:56 PM
 */
@Configuration
public class NotificationClientConfiguration {

    private final ObjectMapper objectMapper;

    public NotificationClientConfiguration(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean(name = "notificationSenderExecutor")
    public ThreadPoolTaskExecutor executor() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(4);
        threadPoolTaskExecutor.setCorePoolSize(64);
        threadPoolTaskExecutor.setQueueCapacity(1024);
        return threadPoolTaskExecutor;
    }
}
