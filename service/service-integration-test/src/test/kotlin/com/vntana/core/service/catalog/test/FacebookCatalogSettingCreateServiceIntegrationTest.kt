package com.vntana.core.service.catalog.test

import com.vntana.core.service.catalog.AbstractFacebookCatalogSettingServiceIntegrationTest
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 10:44 AM
 */
class FacebookCatalogSettingCreateServiceIntegrationTest : AbstractFacebookCatalogSettingServiceIntegrationTest() {

    @Test
    fun `test create`() {
        // given
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val dto = commonTestHelper.buildCreateFacebookCatalogSettingDto(organization = organization)
        // when
        facebookCatalogSettingService.create(dto).let { catalogSetting ->
            // then
            flushAndClear()
            assertThat(catalogSetting)
                .hasFieldOrPropertyWithValue("name", StringUtils.trim(dto.name))
                .hasFieldOrPropertyWithValue("systemUserToken", StringUtils.trim(dto.systemUserToken))
                .hasFieldOrPropertyWithValue("catalogId", dto.catalogId)
        }
    }
}