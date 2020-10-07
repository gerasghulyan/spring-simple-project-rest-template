package com.vntana.core.queue.consumer.boot;

import com.vntana.commons.queue.conf.CommonsQueueAnnotationDrivenConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/7/20
 * Time: 2:46 PM
 */
@Configuration
@Import({CommonsQueueAnnotationDrivenConf.class})
@ComponentScan({"com.vntana.core"})
@EnableJpaRepositories("com.vntana.core.persistence")
@EntityScan("com.vntana.core.domain")
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class QueueMessageConsumerBootApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueMessageConsumerBootApplication.class);

    public static void main(final String[] args) {
        LOGGER.debug("Starting queue message consumer application...");
        SpringApplication.run(QueueMessageConsumerBootApplication.class, args);
    }

    @RestController
    @RequestMapping("/")
    static class HealthCheck {
        @GetMapping("/health")
        public String heartbeat() {
            return "OK";
        }
    }

}
