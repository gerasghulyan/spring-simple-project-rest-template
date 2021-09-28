package com.vntana.core.rest.resource.catalog.test

import com.vntana.core.model.catalog.error.FacebookCatalogSettingErrorResponseModel
import com.vntana.core.rest.resource.catalog.AbstractFacebookCatalogSettingWebTest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 12:38 PM
 */
class FacebookCatalogSettingCreateWebTest : AbstractFacebookCatalogSettingWebTest() {

    @Test
    fun `test create with invalid arguments`() {
        assertBasicErrorResultResponse(
            facebookCatalogSettingResourceClient.create(
                facebookCatalogSettingResourceTestHelper.buildCreateFacebookCatalogSettingRequest(organizationUuid = null)
            ), FacebookCatalogSettingErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(
            facebookCatalogSettingResourceClient.create(
                facebookCatalogSettingResourceTestHelper.buildCreateFacebookCatalogSettingRequest(name = null)
            ), FacebookCatalogSettingErrorResponseModel.MISSING_NAME
        )
        assertBasicErrorResultResponse(
            facebookCatalogSettingResourceClient.create(
                facebookCatalogSettingResourceTestHelper.buildCreateFacebookCatalogSettingRequest(catalogId = null)
            ), FacebookCatalogSettingErrorResponseModel.MISSING_CATALOG_ID
        )
        assertBasicErrorResultResponse(
            facebookCatalogSettingResourceClient.create(
                facebookCatalogSettingResourceTestHelper.buildCreateFacebookCatalogSettingRequest(systemUserToken = null)
            ), FacebookCatalogSettingErrorResponseModel.MISSING_SYSTEM_USER_TOKEN
        )
    }

    @Test
    fun `test create`() {
        val name = uuid()
        val systemUserToken = uuid()
        val catalogId = uuid()
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val request = facebookCatalogSettingResourceTestHelper.buildCreateFacebookCatalogSettingRequest(
            organizationUuid = organizationUuid,
            name = name,
            systemUserToken = systemUserToken,
            catalogId = catalogId
        )
        val response = facebookCatalogSettingResourceClient.create(request)
        facebookCatalogSettingResourceTestHelper.assertBasicSuccessResultResponse(response.body!!)
        assertThat(response.body!!.response().uuid).isNotBlank()
    }
}