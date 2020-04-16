package com.vntana.core.service.token.invitation.organization.impl

import com.vntana.core.service.token.exception.TokenNotFoundException
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
class TokenInvitationOrganizationGetByTokenServiceUnitTest : AbstractTokenInvitationOrganizationServiceUnitTest() {

    @Test
    fun `test getByToken with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { tokenInvitationOrganizationService.getByToken(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenInvitationOrganizationService.getByToken(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test getByToken when not found`() {
        // test data
        resetAll()
        val token = uuid()
        // expectations
        expect(tokenInvitationOrganizationRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThatThrownBy { tokenInvitationOrganizationService.getByToken(token) }.isExactlyInstanceOf(TokenNotFoundException::class.java)
        verifyAll()
    }

    @Test
    fun `test getByToken`() {
        // test data
        val token = uuid()
        val abstractToken = helper.buildTokenInvitationOrganization()
        resetAll()
        // expectations
        expect(tokenInvitationOrganizationRepository.findByToken(token)).andReturn(Optional.of(abstractToken))
        replayAll()
        // test scenario
        assertThat(tokenInvitationOrganizationService.getByToken(token)).isEqualTo(abstractToken)
        verifyAll()
    }
}