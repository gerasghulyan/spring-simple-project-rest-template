package com.vntana.core.helper.rest

import com.vntana.commons.api.model.response.ErrorResponseModel
import com.vntana.commons.api.model.response.ResultResponseModel
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import org.assertj.core.api.Assertions.assertThat

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 11:46 AM
 */
abstract class AbstractRestUnitTestHelper : AbstractCommonTestHelper() {
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