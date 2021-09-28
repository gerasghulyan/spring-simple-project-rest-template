package com.vntana.core.service.catalog.impl

import com.vntana.core.domain.catalog.FacebookCatalogSetting
import com.vntana.core.service.catalog.AbstractFacebookCatalogSettingServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.junit.Test

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 11:06 AM
 */
class FacebookCatalogSettingCreateServiceUnitTest : AbstractFacebookCatalogSettingServiceUnitTest() {

    @Test
    fun `test create with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { facebookCatalogSettingService.create(null) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { facebookCatalogSettingService.create(helper.buildCreateFacebookCatalogSettingDto(name = null)) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { facebookCatalogSettingService.create(helper.buildCreateFacebookCatalogSettingDto(name = emptyString())) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy {
            facebookCatalogSettingService.create(
                helper.buildCreateFacebookCatalogSettingDto(
                    systemUserToken = null
                )
            )
        }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy {
            facebookCatalogSettingService.create(
                helper.buildCreateFacebookCatalogSettingDto(
                    systemUserToken = null
                )
            )
        }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { facebookCatalogSettingService.create(helper.buildCreateFacebookCatalogSettingDto(catalogId = null)) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { facebookCatalogSettingService.create(helper.buildCreateFacebookCatalogSettingDto(catalogId = null)) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy {
            facebookCatalogSettingService.create(
                helper.buildCreateFacebookCatalogSettingDto(
                    organization = null
                )
            )
        }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test create`() {
        // test data
        resetAll()
        val systemUserToken = uuid()
        val name = uuid()
        val catalogId = uuid()
        val organization = organizationTestHelper.buildOrganization()
        val dto = helper.buildCreateFacebookCatalogSettingDto(systemUserToken, name, catalogId, organization)
        // expectations
        EasyMock.expect(facebookCatalogSettingRepository.save(EasyMock.isA(FacebookCatalogSetting::class.java)))
            .andAnswer { EasyMock.getCurrentArguments()[0] as FacebookCatalogSetting }
        replayAll()
        // test scenario
        assertThat(facebookCatalogSettingService.create(dto))
            .hasFieldOrPropertyWithValue("name", dto.name)
            .hasFieldOrPropertyWithValue("catalogId", dto.catalogId)
            .hasFieldOrPropertyWithValue("systemUserToken", dto.systemUserToken)
            .hasFieldOrPropertyWithValue("organization", organization)
        verifyAll()
    }
}