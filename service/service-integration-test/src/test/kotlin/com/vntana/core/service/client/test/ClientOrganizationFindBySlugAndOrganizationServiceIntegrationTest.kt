package com.vntana.core.service.client.test

import com.vntana.core.service.client.AbstractClientOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 12:20 PM
 */
class ClientOrganizationFindBySlugAndOrganizationServiceIntegrationTest : AbstractClientOrganizationServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        // given
        integrationTestHelper.persistClientOrganization()
        clientOrganizationService.findBySlugAndOrganization(uuid(), uuid()).let {
            assertThat(it.isPresent).isFalse()
        }
    }

    @Test
    fun `test`() {
        // given
        val clientOrganization = integrationTestHelper.persistClientOrganization()
        clientOrganizationService.findBySlugAndOrganization(clientOrganization.slug, clientOrganization.organization.uuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.get()).isEqualTo(clientOrganization)
            assertThat(it.get().name).isEqualTo(clientOrganization.name)
            assertThat(it.get().slug).isEqualTo(clientOrganization.slug)
            assertThat(it.get().imageBlobId).isEqualTo(clientOrganization.imageBlobId)
            assertThat(it.get().organization).isEqualTo(clientOrganization.organization)
        }
    }
}