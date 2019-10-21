package com.vntana.core.rest.resource.boot.conf.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 4:29 PM
 */
@Configuration
public class JerseyRestClientConfig {
    @Bean
    public Client client(final ObjectMapper objectMapper) {
        return JerseyClientBuilder.newClient().register(new JacksonJsonProvider(objectMapper)).property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE_CLIENT, true);
    }
}
