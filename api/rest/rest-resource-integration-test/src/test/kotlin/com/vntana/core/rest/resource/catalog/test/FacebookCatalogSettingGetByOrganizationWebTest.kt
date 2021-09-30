package com.vntana.core.rest.resource.catalog.test

import com.vntana.core.model.catalog.error.FacebookCatalogSettingErrorResponseModel
import com.vntana.core.rest.resource.catalog.AbstractFacebookCatalogSettingWebTest
import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.stream.Collectors

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 12:48 PM
 */
class FacebookCatalogSettingGetByOrganizationWebTest : AbstractFacebookCatalogSettingWebTest() {

    @Test
    fun `test when organization not found`() {
        assertBasicErrorResultResponse(
            facebookCatalogSettingResourceClient.getByOrganization(
                facebookCatalogSettingResourceTestHelper.buildGetByOrganizationFacebookCatalogSettingRequest(organizationUuid = uuid())
            ), FacebookCatalogSettingErrorResponseModel.ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun `test getAll`() {
        // given
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val catalog1Uuid =
            facebookCatalogSettingResourceTestHelper.persistFacebookCatalogSetting(organizationUuid = organizationUuid)?.body?.response()?.uuids?.get(0)
        val catalog2Uuid =
            facebookCatalogSettingResourceTestHelper.persistFacebookCatalogSetting(organizationUuid = organizationUuid)?.body?.response()?.uuids?.get(0)

        val expectedCatalogs = mutableListOf(catalog1Uuid, catalog2Uuid)
        val request =
            facebookCatalogSettingResourceTestHelper.buildGetByOrganizationFacebookCatalogSettingRequest(
                organizationUuid = organizationUuid
            )
        // when
        val response = facebookCatalogSettingResourceClient.getByOrganization(request).body!!
        // then
        assertBasicSuccessResultResponse(response)
        val actualCatalogUuids = response.response().items().stream().map { it.uuid }.collect(Collectors.toList())
        Assertions.assertThat(actualCatalogUuids).containsExactlyInAnyOrderElementsOf(expectedCatalogs)
    }
}