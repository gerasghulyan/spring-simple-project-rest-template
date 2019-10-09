package com.vntana.core.model.client.request

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:29 PM
 */
class CheckAvailableClientOrganizationSlugRequestTest {
    @Test
    fun `test validate when has errors`() {
        assertThat(CheckAvailableClientOrganizationSlugRequest(null).validate())
                .isNotEmpty
                .contains(ClientOrganizationErrorResponseModel.MISSING_SLUG)
        assertThat(CheckAvailableClientOrganizationSlugRequest(" ").validate())
                .isNotEmpty
                .contains(ClientOrganizationErrorResponseModel.MISSING_SLUG)
    }
}