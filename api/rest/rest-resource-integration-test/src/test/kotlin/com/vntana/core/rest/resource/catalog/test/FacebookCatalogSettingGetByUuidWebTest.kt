package com.vntana.core.rest.resource.catalog.test

import com.vntana.core.model.catalog.request.CreateFacebookCatalogSettingRequest
import com.vntana.core.model.catalog.request.GetByCatalogIdFacebookCatalogSettingRequest
import com.vntana.core.rest.resource.catalog.AbstractFacebookCatalogSettingWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 12:48 PM
 */
class FacebookCatalogSettingGetByUuidWebTest : AbstractFacebookCatalogSettingWebTest() {

    @Test
    fun `test get by uuid`() {
        // given
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val catalog = facebookCatalogSettingResourceTestHelper.buildSingleFacebookCatalogSetting()

        val uuid = facebookCatalogSettingResourceClient.create(
            CreateFacebookCatalogSettingRequest(
                listOf(catalog),
                uuid(),
                organizationUuid
            )
        ).body?.response()?.uuids?.get(0)
        // when
        val response = facebookCatalogSettingResourceClient.getByUuid(uuid).body!!
        // then
        assertBasicSuccessResultResponse(response)
        assertThat(response.response().uuid).isEqualTo(uuid)
        assertThat(response.response().name).isEqualTo(catalog.name)
    }
}