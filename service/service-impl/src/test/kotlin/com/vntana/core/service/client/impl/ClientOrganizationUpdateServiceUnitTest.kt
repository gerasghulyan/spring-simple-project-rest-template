package com.vntana.core.service.client.impl

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.service.client.AbstractClientOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan.
 * Date: 10/8/19
 * Time: 6:04 PM
 */
class ClientOrganizationUpdateServiceUnitTest : AbstractClientOrganizationServiceUnitTest() {
    @Test
    fun `test with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { clientOrganizationService.update(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.update(helper.buildUpdateClientOrganizationDto(uuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.update(helper.buildUpdateClientOrganizationDto(uuid = "")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.update(helper.buildUpdateClientOrganizationDto(name = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.update(helper.buildUpdateClientOrganizationDto(name = "")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        // test data
        resetAll()
        val imageBlobId = uuid()
        val clientOrganization = helper.buildClientOrganization()
        val dto = helper.buildUpdateClientOrganizationDto(imageBlobId = imageBlobId, uuid = clientOrganization.uuid)
        // expectations
        expect(clientOrganizationRepository.findByUuid(clientOrganization.uuid)).andReturn(Optional.of(clientOrganization))
        expect(clientOrganizationRepository.save(isA(ClientOrganization::class.java)))
                .andAnswer { getCurrentArguments()[0] as ClientOrganization }
        replayAll()
        // test scenario
        assertThat(clientOrganizationService.update(dto))
                .hasFieldOrPropertyWithValue("name", dto.name)
                .hasFieldOrPropertyWithValue("imageBlobId", dto.imageBlobId)
                .hasFieldOrPropertyWithValue("organization", clientOrganization.organization)
                .hasFieldOrPropertyWithValue("uuid", dto.uuid)
        verifyAll()
    }
}