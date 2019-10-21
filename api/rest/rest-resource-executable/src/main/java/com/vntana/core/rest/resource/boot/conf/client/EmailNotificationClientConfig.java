package com.vntana.core.rest.resource.boot.conf.client;

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient;
import com.sflpro.notifier.api.client.notification.email.impl.EmailNotificationResourceClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 4:26 PM
 */
@Configuration
public class EmailNotificationClientConfig {
    @Bean
    public EmailNotificationResourceClient emailNotificationResourceClient(final Client client, @Value("${ms.notifications.url}") final String apiPath) {
        return new EmailNotificationResourceClientImpl(client, apiPath);
    }
}
