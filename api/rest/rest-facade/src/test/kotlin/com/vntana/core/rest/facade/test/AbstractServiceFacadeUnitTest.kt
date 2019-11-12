package com.vntana.core.rest.facade.test

import com.vntana.commons.api.model.response.ErrorResponseModel
import com.vntana.commons.api.model.response.ResultResponseModel
import org.assertj.core.api.Assertions
import org.easymock.EasyMockRunner
import org.easymock.EasyMockSupport
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:55 PM
 */
@RunWith(EasyMockRunner::class)
abstract class AbstractServiceFacadeUnitTest : EasyMockSupport() {
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