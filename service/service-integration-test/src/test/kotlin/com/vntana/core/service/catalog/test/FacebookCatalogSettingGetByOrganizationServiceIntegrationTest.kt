package com.vntana.core.service.catalog.test

import com.vntana.core.service.catalog.AbstractFacebookCatalogSettingServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 10:48 AM
 */
class FacebookCatalogSettingGetByOrganizationServiceIntegrationTest :
    AbstractFacebookCatalogSettingServiceIntegrationTest() {

    @Test
    fun `test getAll`() {
        val expectedOrganization = organizationIntegrationTestHelper.persistOrganization()
        val unexpectedOrganization = organizationIntegrationTestHelper.persistOrganization()
        val expectedCatalog =
            integrationTestHelper.persistFacebookCatalogSetting(organization = expectedOrganization)
        flushAndClear()
        val deletedCatalog =
            integrationTestHelper.persistFacebookCatalogSetting(organization = expectedOrganization)
        integrationTestHelper.deleteFacebookCatalogSetting(deletedCatalog)
        val unexpectedCatalog =
            integrationTestHelper.persistFacebookCatalogSetting(organization = unexpectedOrganization)
        flushAndClear()
        val dto =
            integrationTestHelper.buildGetByOrganizationFacebookCatalogSettingDto(organization = expectedOrganization)
        facebookCatalogSettingService.getByOrganization(dto).let {
            assertThat(it.content).isNotEmpty
            assertThat(it.totalElements).isEqualTo(1)
            assertThat(it.totalPages).isEqualTo(dto.page + 1)
            assertThat(it).doesNotContain(unexpectedCatalog)
            assertThat(it).doesNotContain(deletedCatalog)
            it.get().forEach { assertThat(it.uuid).isEqualTo(expectedCatalog.uuid) }
        }
    }
}
