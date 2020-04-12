package com.vntana.core.service.token.invitation.organization.impl

import com.vntana.core.service.token.AbstractTokenServiceUnitTest
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
class TokenInvitationOrganizationExistsByTokenServiceUnitTest : AbstractTokenInvitationOrganizationServiceUnitTest() {

    @Test
    fun `test existsByToken with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { tokenInvitationOrganizationService.existsByToken(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenInvitationOrganizationService.existsByToken(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test existsByToken when not found`() {
        // test data
        resetAll()
        val token = uuid()
        // expectations
        expect(tokenInvitationOrganizationRepository.existsByToken(token)).andReturn(false)
        replayAll()
        // test scenario
        assertThat(tokenInvitationOrganizationService.existsByToken(token)).isFalse()
        verifyAll()
    }

    @Test
    fun `test existsByToken`() {
        // test data
        val token = uuid()
        val abstractToken = helper.buildTokenInvitationOrganization()
        resetAll()
        // expectations
        expect(tokenInvitationOrganizationRepository.existsByToken(token)).andReturn(true)
        replayAll()
        // test scenario
        assertThat(tokenInvitationOrganizationService.existsByToken(token)).isTrue()
        verifyAll()
    }
}