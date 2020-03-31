package com.vntana.core.service.invitation.organization.mediator.impl

import com.vntana.commons.indexation.payload.EntityLifecycle
import com.vntana.core.listener.invitation.organization.InvitationOrganizationUuidAwareLifecyclePayload
import com.vntana.core.service.invitation.organization.mediator.AbstractInvitationOrganizationUuidAwareLifecycleMediatorUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 6:32 PM
 */
class InvitationOrganizationLifecycleMediatorOnDeleteUnitTest : AbstractInvitationOrganizationUuidAwareLifecycleMediatorUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationOrganizationUuidAwareLifecycleMediator.onCreated(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val invitationOrganization = helper.buildInvitationOrganization()
        resetAll()
        expect(applicationEventPublisher.publishEvent(
                InvitationOrganizationUuidAwareLifecyclePayload(invitationOrganization.uuid, EntityLifecycle.DELETED)
        ))
        replayAll()
        invitationOrganizationUuidAwareLifecycleMediator.onDeleted(invitationOrganization.uuid)
        verifyAll()
    }
}