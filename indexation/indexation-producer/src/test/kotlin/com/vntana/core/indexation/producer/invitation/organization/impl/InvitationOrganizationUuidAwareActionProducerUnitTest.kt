package com.vntana.core.indexation.producer.invitation.organization.impl

import com.vntana.commons.queue.model.MessageActionType
import com.vntana.core.indexation.consumer.message.invitation.organization.InvitationOrganizationUuidAwareActionQueueMessage
import com.vntana.core.indexation.producer.invitation.organization.AbstractInvitationOrganizationUuidAwareActionProducerUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 6:27 PM
 */
class InvitationOrganizationUuidAwareActionProducerUnitTest : AbstractInvitationOrganizationUuidAwareActionProducerUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationOrganizationUuidAwareActionProducer.produce(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test produce failed`() {
        val message = InvitationOrganizationUuidAwareActionQueueMessage(MessageActionType.CREATED, uuid())
        resetAll()
        expect(kafkaTemplate.send(preIndexationInvitationOrganizationTopic, message.uuid, message)).andReturn(listenableFuture)
        expect(listenableFuture.get(5000L, TimeUnit.MILLISECONDS)).andThrow(TimeoutException())
        replayAll()
        assertThatThrownBy { invitationOrganizationUuidAwareActionProducer.produce(message) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun `test produce`() {
        resetAll()
        val message = InvitationOrganizationUuidAwareActionQueueMessage(MessageActionType.CREATED, uuid())
        expect(kafkaTemplate.send(preIndexationInvitationOrganizationTopic, message.uuid, message)).andReturn(listenableFuture)
        expect(listenableFuture.get(5000L, TimeUnit.MILLISECONDS)).andReturn(sendResult)
        replayAll()
        invitationOrganizationUuidAwareActionProducer.produce(message)
        verifyAll()
    }
}