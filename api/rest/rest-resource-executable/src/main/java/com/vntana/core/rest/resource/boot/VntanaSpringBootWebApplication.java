package com.vntana.core.rest.resource.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 5:10 PM
 */
@SpringBootApplication
public class VntanaSpringBootWebApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(VntanaSpringBootWebApplication.class);

    public static void main(String[] args) {
        LOGGER.debug("Starting Vntana Web application");
        SpringApplication.run(VntanaSpringBootWebApplication.class, args);
    }
}
