package com.project.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Geras Ghulyan.
 * Date: 10/4/19
 * Time: 5:10 PM
 */
@SpringBootApplication
public class SpringBootWebApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootWebApplication.class);

    public static void main(String[] args) {
        LOGGER.debug("Starting Web application");
        SpringApplication.run(SpringBootWebApplication.class, args);
    }
}
