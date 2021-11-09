package com.project.sample.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Geras Ghulyan.
 * Date: 10/4/19
 * Time: 12:03 PM
 */
@SpringBootApplication
public class MigrationBootApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MigrationBootApplication.class);

    public static void main(String[] args) {
        LOGGER.debug("Starting flyway migration");
        SpringApplication.run(MigrationBootApplication.class, args);
    }
}
