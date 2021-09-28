package com.vntana.core.rest.resource.catalog.test

import com.vntana.core.model.catalog.error.FacebookCatalogSettingErrorResponseModel
import com.vntana.core.rest.resource.catalog.AbstractFacebookCatalogSettingWebTest
import org.assertj.core.api.AssertionsForInterfaceTypes
import org.junit.Test

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 1:02 PM
 */
class FacebookCatalogSettingDeleteWebTest : AbstractFacebookCatalogSettingWebTest() {

    @Test
    fun `test when facebook catalog setting not found`() {
        assertBasicErrorResultResponse(
            facebookCatalogSettingResourceClient.delete(uuid()),
            FacebookCatalogSettingErrorResponseModel.FACEBOOK_CATALOG_SETTING_NOT_FOUND
        )
    }

    @Test
    fun `test delete`() {
        val name = uuid()
        val systemUserToken = uuid()
        val catalogId = uuid()
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val catalogUuid = facebookCatalogSettingResourceTestHelper.persistFacebookCatalogSetting(
            organizationUuid = organizationUuid,
            name = name,
            systemUserToken = systemUserToken,
            catalogId = catalogId
        )?.body?.response()?.uuid

        val response = facebookCatalogSettingResourceClient.delete(catalogUuid)
        facebookCatalogSettingResourceTestHelper.assertBasicSuccessResultResponse(response.body!!)
        AssertionsForInterfaceTypes.assertThat(response.body!!.response().uuid).isNotBlank()
    }
}