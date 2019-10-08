package com.vntana.core.rest.resource.boot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 5:23 PM
 */
@Configuration
@PropertySource("classpath:rest-application.properties")
public class WebApplicationConfiguration {
}
