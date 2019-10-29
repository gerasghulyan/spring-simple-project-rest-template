package com.vntana.core.notification.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient;
import com.sflpro.notifier.api.client.notification.email.impl.EmailNotificationResourceClientImpl;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.ws.rs.client.Client;

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

    @Bean
    public EmailNotificationResourceClient emailNotificationResourceClient(@Value("${vntana.notification.url}") final String apiPath) {
        return new EmailNotificationResourceClientImpl(client(), apiPath);
    }

    @Bean
    public Client client() {
        return JerseyClientBuilder.newClient()
                .register(new JacksonJsonProvider(objectMapper));
    }
}
