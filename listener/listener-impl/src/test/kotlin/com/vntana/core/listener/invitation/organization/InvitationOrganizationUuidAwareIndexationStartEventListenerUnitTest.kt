package com.vntana.core.listener.invitation.organization

import com.vntana.cache.service.organization.CombinedOrganizationLockService
import com.vntana.commons.indexation.payload.EntityLifecycle
import com.vntana.commons.queue.model.MessageActionType
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.indexation.consumer.message.invitation.organization.InvitationOrganizationUuidAwareActionQueueMessage
import com.vntana.core.indexation.producer.invitation.organization.InvitationOrganizationUuidAwareActionProducer
import com.vntana.core.listener.AbstractListenerUnitTest
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import org.assertj.core.api.Assertions
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 6:21 PM
 */
class InvitationOrganizationUuidAwareIndexationStartEventListenerUnitTest : AbstractListenerUnitTest() {

    private lateinit var indexationStartEventListener: InvitationOrganizationUuidAwareIndexationStartEventListener

    private val commonTestHelper = InvitationOrganizationCommonTestHelper()

    @Mock
    private lateinit var actionProducer: InvitationOrganizationUuidAwareActionProducer

    @Mock
    private lateinit var invitationOrganizationService: InvitationOrganizationService
    
    @Mock
    private lateinit var combinedOrganizationLockService: CombinedOrganizationLockService

    @Before
    fun prepare() {
        indexationStartEventListener = InvitationOrganizationUuidAwareIndexationStartEventListener(invitationOrganizationService,
                combinedOrganizationLockService,
                actionProducer
        )
    }

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        Assertions.assertThatThrownBy { indexationStartEventListener.handleEvent(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test handleEvent`() {
        val invitationOrganization = commonTestHelper.buildInvitationOrganization()
        val payload = InvitationOrganizationUuidAwareLifecyclePayload(invitationOrganization.uuid, EntityLifecycle.CREATED)
        val message = InvitationOrganizationUuidAwareActionQueueMessage(MessageActionType.CREATED, invitationOrganization.uuid)
        resetAll()
        expect(invitationOrganizationService.getByUuid(invitationOrganization.uuid)).andReturn(invitationOrganization)
        expect(combinedOrganizationLockService.lock(invitationOrganization.uuid)).andVoid()
        expect(actionProducer.produce(message)).andVoid()
        replayAll()
        indexationStartEventListener.handleEvent(payload)
        verifyAll()
    }

    @Test
    fun `test handleEvent update when organization removed`() {
        val invitationOrganization = commonTestHelper.buildInvitationOrganization()
        invitationOrganization.removed = LocalDateTime.now()
        val payload = InvitationOrganizationUuidAwareLifecyclePayload(invitationOrganization.uuid, EntityLifecycle.UPDATED)
        resetAll()
        expect(invitationOrganizationService.getByUuid(invitationOrganization.uuid)).andReturn(invitationOrganization)
        replayAll()
        indexationStartEventListener.handleEvent(payload)
        verifyAll()
    }
}