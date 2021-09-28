package com.vntana.core.service.catalog.test

import com.vntana.core.service.catalog.AbstractFacebookCatalogSettingServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 11:29 AM
 */
class FacebookCatalogSettingFindByUuidServiceIntegrationTest : AbstractFacebookCatalogSettingServiceIntegrationTest() {

    @Test
    fun `test findByUuid`() {
        // given
        integrationTestHelper.persistFacebookCatalogSetting().let { catalog ->
            // when
            flushAndClear()
            facebookCatalogSettingService.findByUuid(catalog.uuid).let {
                // then
                assertThat(it).isPresent
                assertThat(it.get().name).isEqualTo(catalog.name)
                assertThat(it.get().catalogId).isEqualTo(catalog.catalogId)
                assertThat(it.get().systemUserToken).isEqualTo(catalog.systemUserToken)
                assertThat(it.get().organization.uuid).isEqualTo(catalog.organization.uuid)
            }
        }
    }
}