package com.vntana.core.rest.resource.boot.conf;

import com.vntana.cache.conf.VntanaCacheConf;
import com.vntana.commons.queue.conf.CommonsQueueAnnotationDrivenConf;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 5:23 PM
 */
@Import({CommonsQueueAnnotationDrivenConf.class, VntanaCacheConf.class})
@Configuration
@EnableFeignClients({"com.vntana.core.rest.client", "com.vntana.payment.client"})
@ComponentScan({"com.vntana.core"})
@EnableJpaRepositories("com.vntana.core.persistence")
@EntityScan("com.vntana.core.domain")
@PropertySource("classpath:application.properties")
public class WebApplicationConfiguration {

    @Bean(name = "notificationSenderExecutor")
    public ThreadPoolTaskExecutor executor() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(4);
        threadPoolTaskExecutor.setCorePoolSize(64);
        threadPoolTaskExecutor.setQueueCapacity(1024);
        return threadPoolTaskExecutor;
    }
}
