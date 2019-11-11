package com.vntana.core.rest.resource

import com.vntana.commons.api.model.response.ErrorResponseModel
import com.vntana.commons.api.model.response.ResultResponseModel
import com.vntana.core.rest.resource.boot.WebApplication
import com.vntana.core.rest.resource.conf.WebIntegrationTestConfiguration
import org.assertj.core.api.Assertions
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
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

    fun assertBasicSuccessResultResponse(resultResponse: ResultResponseModel<*, *>) {
        Assertions.assertThat(resultResponse).isNotNull
        Assertions.assertThat(resultResponse.success()).isTrue()
        Assertions.assertThat(resultResponse.errors()).isEmpty()
    }

    fun assertBasicErrorResultResponse(resultResponse: ResultResponseModel<*, *>, vararg errors: ErrorResponseModel) {
        Assertions.assertThat(resultResponse).isNotNull
        Assertions.assertThat(resultResponse.success()).isFalse()
        Assertions.assertThat(resultResponse.errors()).isNotEmpty.containsExactlyInAnyOrder(*errors)
    }
}