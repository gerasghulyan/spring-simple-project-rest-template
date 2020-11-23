package com.vntana.core.service.client.impl

import com.vntana.core.service.client.AbstractClientOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 2/13/20
 * Time: 2:55 PM
 */
class ClientOrganizationGetByUuidServiceUnitTest : AbstractClientOrganizationServiceUnitTest() {

    @Test
    fun `test when not found`() {
        val uuid = uuid()
        resetAll()
        expect(clientOrganizationRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { organizationClientService.getByUuid(uuid) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun `test when found`() {
        val clientOrganization = helper.buildClientOrganization()
        resetAll()
        expect(clientOrganizationRepository.findByUuid(clientOrganization.uuid)).andReturn(Optional.of(clientOrganization))
        replayAll()
        assertThat(organizationClientService.getByUuid(clientOrganization.uuid)).isEqualTo(clientOrganization)
        verifyAll()
    }
}