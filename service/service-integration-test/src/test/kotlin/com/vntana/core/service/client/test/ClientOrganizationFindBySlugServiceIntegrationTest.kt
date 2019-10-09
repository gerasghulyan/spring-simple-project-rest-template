package com.vntana.core.service.client.test

import com.vntana.core.service.client.AbstractClientOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 12:20 PM
 */
class ClientOrganizationFindBySlugServiceIntegrationTest : AbstractClientOrganizationServiceIntegrationTest() {
    @Test
    fun `test findByUuid`() {
        // given
        integrationTestHelper.persistClientOrganization().let { clientOrganization ->
            // when
            flushAndClear()
            clientOrganizationService.findBySlug(clientOrganization.slug).let {
                // then
                assertThat(it).hasValue(clientOrganization)
            }
        }
    }
}