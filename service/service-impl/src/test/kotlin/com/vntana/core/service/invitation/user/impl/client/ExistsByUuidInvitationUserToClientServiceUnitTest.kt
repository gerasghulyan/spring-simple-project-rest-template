package com.vntana.core.service.invitation.user.impl.client

import com.vntana.core.service.invitation.user.AbstractUserInvitationToClientServiceUnitTest
import org.assertj.core.api.Assertions.*
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 6:55 PM
 */
class ExistsByUuidInvitationUserToClientServiceUnitTest : AbstractUserInvitationToClientServiceUnitTest() {
    
    @Test
    fun `test when uuid is empty`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserToClientService.existsByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
    }
}