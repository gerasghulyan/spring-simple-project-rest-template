package com.vntana.core.service.catalog.impl

import com.vntana.core.service.catalog.AbstractFacebookCatalogSettingServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 11:26 AM
 */
class FacebookCatalogSettingGetByUuidServiceUnitTest : AbstractFacebookCatalogSettingServiceUnitTest() {

    @Test
    fun `test when not found`() {
        val uuid = uuid()
        resetAll()
        EasyMock.expect(facebookCatalogSettingRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(facebookCatalogSettingService.findByUuid(uuid)).isEmpty
        verifyAll()
    }

    @Test
    fun `test when found`() {
        val catalog = helper.buildFacebookCatalogSetting()
        resetAll()
        EasyMock.expect(facebookCatalogSettingRepository.findByUuid(catalog.uuid))
            .andReturn(Optional.of(catalog))
        replayAll()
        assertThat(facebookCatalogSettingService.findByUuid(catalog.uuid)).isEqualTo(catalog)
        verifyAll()
    }
}