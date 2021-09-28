package com.vntana.core.service.catalog.impl

import com.vntana.core.service.catalog.AbstractFacebookCatalogSettingServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.junit.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 11:15 AM
 */
class FacebookCatalogSettingGetByOrganizationServiceUnitTest : AbstractFacebookCatalogSettingServiceUnitTest() {

    @Test
    fun `test with invalid argument`() {
        resetAll()
        replayAll()
        assertThatThrownBy { facebookCatalogSettingService.getByOrganization(null) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test getAll not found`() {
        val organization = organizationTestHelper.buildOrganization()
        val dto = helper.buildGetByOrganizationFacebookCatalogSettingDto(organization = organization)
        resetAll()
        EasyMock.expect(
            facebookCatalogSettingRepository.getAllByOrganizationAndRemovedIsNull(
                organization,
                PageRequest.of(dto.page, dto.size)
            )
        ).andReturn(Page.empty())
        replayAll()
        facebookCatalogSettingService.getByOrganization(dto).let {
            assertThat(it).isEmpty()
        }
        verifyAll()
    }

    @Test
    fun `test getAll`() {
        val organization = organizationTestHelper.buildOrganization()
        val catalogs = listOf(
            helper.buildFacebookCatalogSetting(organization = organization),
            helper.buildFacebookCatalogSetting(organization = organization)
        )
        val dto =
            helper.buildGetByOrganizationFacebookCatalogSettingDto(size = catalogs.size, organization = organization)
        resetAll()
        EasyMock.expect(
            facebookCatalogSettingRepository.getAllByOrganizationAndRemovedIsNull(
                dto.organization,
                PageRequest.of(dto.page, dto.size)
            )
        ).andReturn(
            helper.buildFacebookCatalogSettingsPage(
                catalogs = catalogs,
                pageAble = helper.buildPageRequest(dto.page, dto.size)
            )
        )
        replayAll()
        facebookCatalogSettingService.getByOrganization(dto).let {
            assertThat(it.content).isNotEmpty
            assertThat(it.totalPages).isEqualTo(dto.page + 1)
            assertThat(it.totalElements).isEqualTo(dto.size.toLong())
            assertThat(it).containsExactlyElementsOf(catalogs)
        }
        verifyAll()
    }
}