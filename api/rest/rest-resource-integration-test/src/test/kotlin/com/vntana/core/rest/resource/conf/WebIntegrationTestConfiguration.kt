package com.vntana.core.rest.resource.conf

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.util.concurrent.Executor

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:46 AM
 */
@EnableFeignClients("com.vntana.core")
@ComponentScan("com.vntana.core")
@EnableJpaRepositories("com.vntana.core")
@EntityScan("com.vntana.core")
@TestConfiguration
class WebIntegrationTestConfiguration {

    @Primary
    @Lazy(false)
    @Bean(name = ["notificationSenderExecutor"])
    fun executor(): Executor = Executor { it.run() }

}