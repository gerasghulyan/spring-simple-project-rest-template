package com.vntana.core.rest.resource

import com.vntana.core.rest.resource.boot.WebApplication
import com.vntana.core.rest.resource.conf.IntegrationTestConfiguration
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:20 AM
 */
@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [WebApplication::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [IntegrationTestConfiguration::class, WebApplication::class])
abstract class AbstractWebIntegrationTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @LocalServerPort
    var port: Int = 0

    fun uuid(): String = UUID.randomUUID().toString()

    abstract fun baseMapping(): String

    abstract fun endpointMapping(): String
}