package com.vntana.core.rest.resource

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient
import com.vntana.core.rest.resource.boot.WebApplication
import com.vntana.core.rest.resource.conf.WebIntegrationTestConfiguration
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = [WebIntegrationTestConfiguration::class, WebApplication::class])
abstract class AbstractWebIntegrationTest {

    @LocalServerPort
    var port: Int = 0

    fun uuid(): String = UUID.randomUUID().toString()

    @MockBean
    protected lateinit var emailNotificationResourceClient: EmailNotificationResourceClient
}