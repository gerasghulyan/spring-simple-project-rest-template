package com.vntana.core.helper

import com.vntana.commons.api.model.response.ErrorResponseModel
import com.vntana.commons.api.model.response.ResultResponseModel
import org.assertj.core.api.Assertions.assertThat
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 10/23/19
 * Time: 4:57 PM
 */
abstract class AbstractRestTestHelper {
    fun uuid(): String = UUID.randomUUID().toString()

    fun assertBasicSuccessResultResponse(resultResponse: ResultResponseModel<*, *>) {
        assertThat(resultResponse).isNotNull
        assertThat(resultResponse.success()).isTrue()
        assertThat(resultResponse.errors()).isEmpty()
    }

    fun assertBasicErrorResultResponse(resultResponse: ResultResponseModel<*, *>, vararg errors: ErrorResponseModel) {
        assertThat(resultResponse).isNotNull
        assertThat(resultResponse.success()).isFalse()
        assertThat(resultResponse.errors()).isNotEmpty.containsExactlyInAnyOrder(*errors)
    }
}