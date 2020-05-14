package com.vntana.core.service.token.invitation.organization.impl

import com.vntana.core.service.token.invitation.organization.AbstractTokenInvitationOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan
 * Date: 12.04.20
 * Time: 02:00
 */
class TokenInvitationOrganizationFindByTokenServiceUnitTest : AbstractTokenInvitationOrganizationServiceUnitTest() {
    @Test
    fun `test with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { tokenInvitationOrganizationService.findByToken(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        // test data
        resetAll()
        val token = uuid()
        // expectations
        expect(tokenInvitationOrganizationRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThat(tokenInvitationOrganizationService.findByToken(token)).isEmpty
        verifyAll()
    }

    @Test
    fun `test when found`() {
        // test data
        resetAll()
        val token = uuid()
        val abstractToken = helper.buildTokenInvitationOrganization()
        // expectations
        expect(tokenInvitationOrganizationRepository.findByToken(token)).andReturn(Optional.of(abstractToken))
        replayAll()
        // test scenario
        assertThat(tokenInvitationOrganizationService.findByToken(token)).hasValue(abstractToken)
        verifyAll()
    }
}