package com.vntana.core.rest.resource

import com.vntana.commons.api.model.constants.Headers
import com.vntana.commons.api.model.response.ErrorResponseModel
import com.vntana.commons.api.model.response.ResultResponseModel
import com.vntana.core.rest.resource.boot.WebApplication
import com.vntana.core.rest.resource.conf.WebIntegrationTestConfiguration
import org.assertj.core.api.Assertions
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    fun assertBasicSuccessResultResponse(responseEntity: ResponseEntity<out ResultResponseModel<*, *>>) {
        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.body).isNotNull
        responseEntity.body?.let {
            Assertions.assertThat(it.success()).isTrue()
            Assertions.assertThat(it.errors()).isEmpty()
        }
    }

    fun assertBasicErrorResultResponse(responseEntity: ResponseEntity<out ResultResponseModel<*, *>>, vararg errors: ErrorResponseModel) {
        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.body).isNotNull
        responseEntity.body?.let {
            Assertions.assertThat(it.success()).isFalse()
            Assertions.assertThat(it.errors()).isNotEmpty.containsExactlyInAnyOrder(*errors)
        }
    }

    fun assertBasicErrorResultResponse(httpStatus: HttpStatus, responseEntity: ResponseEntity<out ResultResponseModel<*, *>>, vararg errors: ErrorResponseModel) {
        assertBasicErrorResultResponse(responseEntity = responseEntity, errors = *errors)
        Assertions.assertThat(responseEntity.headers[Headers.STATUS]?.get(0)).isEqualTo(httpStatus.value().toString())
    }

}