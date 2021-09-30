package com.vntana.core.rest.resource.catalog.test

import com.vntana.core.model.catalog.error.FacebookCatalogSettingErrorResponseModel
import com.vntana.core.model.catalog.request.GetByCatalogIdFacebookCatalogSettingRequest
import com.vntana.core.rest.resource.catalog.AbstractFacebookCatalogSettingWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 12:48 PM
 */
class FacebookCatalogSettingGetByCatalogIdWebTest : AbstractFacebookCatalogSettingWebTest() {

    @Test
    fun `test when not found`() {
        assertBasicErrorResultResponse(
            facebookCatalogSettingResourceClient.getByCatalogId(
                GetByCatalogIdFacebookCatalogSettingRequest(uuid(), uuid())
            ), FacebookCatalogSettingErrorResponseModel.FACEBOOK_CATALOG_SETTING_NOT_FOUND
        )
    }

    @Test
    fun `test getAll`() {
        // given
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val expectedCatalog =
            facebookCatalogSettingResourceTestHelper.persistFacebookCatalogSetting(organizationUuid = organizationUuid)?.body?.response()

        val request = GetByCatalogIdFacebookCatalogSettingRequest(expectedCatalog?.catalogId, organizationUuid)
        // when
        val response = facebookCatalogSettingResourceClient.getByCatalogId(request).body!!
        // then
        assertBasicSuccessResultResponse(response)
        assertThat(response.response().uuid).isEqualTo(expectedCatalog?.uuid)
    }
}