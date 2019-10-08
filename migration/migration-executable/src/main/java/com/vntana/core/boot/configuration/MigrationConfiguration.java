package com.vntana.core.boot.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 12:35 PM
 */
@Configuration
@ComponentScan("com.vntana.core")
@PropertySource("classpath:migration-application.properties")
public class MigrationConfiguration {

}
