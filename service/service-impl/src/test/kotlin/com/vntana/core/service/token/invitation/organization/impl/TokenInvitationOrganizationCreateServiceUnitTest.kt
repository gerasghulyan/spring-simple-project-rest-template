package com.vntana.core.service.token.invitation.organization.impl

import com.vntana.core.domain.token.TokenInvitationOrganization
import com.vntana.core.service.token.AbstractTokenServiceUnitTest
import com.vntana.core.service.token.invitation.organization.AbstractTokenInvitationOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 11:14 AM
 */
class TokenInvitationOrganizationCreateServiceUnitTest : AbstractTokenInvitationOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { helper.buildCreateTokenInvitationOrganizationDto(token = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { helper.buildCreateTokenInvitationOrganizationDto(token = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { helper.buildCreateTokenInvitationOrganizationDto(invitationOrganizationUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { helper.buildCreateTokenInvitationOrganizationDto(invitationOrganizationUuid = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenInvitationOrganizationService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val invitationOrganization = invitationOrganizationCommonTestHelper.buildInvitationOrganization()
        val dto = helper.buildCreateTokenInvitationOrganizationDto(
                invitationOrganizationUuid = invitationOrganization.uuid
        )
        expect(invitationOrganizationService.getByUuid(dto.invitationOrganizationUuid)).andReturn(invitationOrganization)
        expect(tokenRepository.save(isA(TokenInvitationOrganization::class.java))).andAnswer { getCurrentArguments()[0] as TokenInvitationOrganization }
        replayAll()
        tokenInvitationOrganizationService.create(dto).let {
            assertThat(it.token).isEqualTo(dto.token)
            assertThat(it.invitationOrganization.uuid).isEqualTo(dto.invitationOrganizationUuid)
        }
        verifyAll()
    }
}