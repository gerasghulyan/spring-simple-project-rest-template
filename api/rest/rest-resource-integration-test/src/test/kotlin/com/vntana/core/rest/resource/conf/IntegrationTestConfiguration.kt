package com.vntana.core.rest.resource.conf

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:46 AM
 */
@TestConfiguration
@ComponentScan("com.vntana.core", "com.vntana.core.rest.resource")
@EnableJpaRepositories("com.vntana.core")
@EntityScan("com.vntana.core")
class IntegrationTestConfiguration