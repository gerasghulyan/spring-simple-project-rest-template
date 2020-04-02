package com.vntana.core.rest.resource.indexation.impl

import com.vntana.commons.queue.model.MessageActionType
import com.vntana.core.rest.resource.indexation.AbstractIndexationWebTest
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by Arman Gevorgyan.
 * Date: 4/2/20
 * Time: 12:36 PM
 */
class IndexationIndexAllOrganizationInvitationsWebTest : AbstractIndexationWebTest() {

    @Test
    fun `test when no deleted`() {
        val invitation1Uuid = invitationOrganizationResourceTestHelper.persistInvitationOrganization()
        val invitation2Uuid = invitationOrganizationResourceTestHelper.persistInvitationOrganization()
        assertBasicSuccessResultResponse(indexationResourceClient.indexAllOrganizationInvitations())
        Mockito.verify(invitationOrganizationUuidAwareActionProducer).produce(Mockito.argThat { argument ->
            argument.messageActionType == MessageActionType.UPDATED && argument.uuid == invitation1Uuid
        })
        Mockito.verify(invitationOrganizationUuidAwareActionProducer).produce(Mockito.argThat { argument ->
            argument.messageActionType == MessageActionType.UPDATED && argument.uuid == invitation2Uuid
        })
    }
}