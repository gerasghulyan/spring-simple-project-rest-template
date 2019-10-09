package com.vntana.core.service.client.test

import com.vntana.core.service.client.AbstractClientOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 10:51 AM
 */
class CreateClientOrganizationServiceIntegrationTest : AbstractClientOrganizationServiceIntegrationTest() {
    @Test
    fun `test create`() {
        // given
        commonTestHelper.buildCreateClientOrganizationDto().let { dto ->
            // when
            clientOrganizationService.create(dto).let { clientOrganization ->
                // then
                flushAndClear()
                assertThat(clientOrganization)
                        .hasFieldOrPropertyWithValue("name", dto.name)
                        .hasFieldOrPropertyWithValue("slug", dto.slug)
            }
        }
    }
}