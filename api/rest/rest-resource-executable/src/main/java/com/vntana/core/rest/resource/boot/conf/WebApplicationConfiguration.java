package com.vntana.core.rest.resource.boot.conf;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 5:23 PM
 */
@Configuration
@ComponentScan("com.vntana.core")
@EnableJpaRepositories("com.vntana.core.persistence")
@EntityScan("com.vntana.core.domain")
@PropertySource("classpath:application.properties")
public class WebApplicationConfiguration {
}
