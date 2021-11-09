package com.project.sample.migration.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Geras Ghulyan.
 * Date: 10/4/19
 * Time: 12:35 PM
 */
@Configuration
@ComponentScan("com.project.sample")
@PropertySource("classpath:migration-application.properties")
public class MigrationConfiguration {

}
