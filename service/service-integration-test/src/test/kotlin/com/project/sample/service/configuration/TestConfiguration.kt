package com.project.sample.service.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

/**
 * Created by Geras Ghulyan.
 * Date: 10/4/19
 * Time: 3:05 PM
 */
@TestConfiguration
@Lazy(false)
@ComponentScan("com.project.sample")
@EnableJpaRepositories("com.project.sample")
@EntityScan("com.project.sample")
class TestConfiguration {

    @Bean
    @Primary
    fun executor(): ThreadPoolTaskExecutor = ThreadPoolTaskExecutor()
}