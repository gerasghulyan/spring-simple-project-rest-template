package com.vntana.core.service.client.test

import com.vntana.core.service.client.AbstractClientOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 10:51 AM
 */
class ClientOrganizationUpdateServiceIntegrationTest : AbstractClientOrganizationServiceIntegrationTest() {

    @Test
    fun test() {
        val client = integrationTestHelper.persistClientOrganization()
        val dto = commonTestHelper.buildUpdateClientOrganizationDto(uuid = client.uuid)
        flushAndClear()
        clientOrganizationService.update(dto).let {
            assertThat(it.uuid).isEqualTo(client.uuid)
            assertThat(it.name).isEqualTo(dto.name)
            assertThat(it.imageId).isEqualTo(dto.imageId)
            assertThat(it.organization).isEqualTo(client.organization)
        }
    }
}