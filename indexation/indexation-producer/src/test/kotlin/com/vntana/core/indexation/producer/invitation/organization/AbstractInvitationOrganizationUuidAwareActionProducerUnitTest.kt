package com.vntana.core.indexation.producer.invitation.organization

import com.vntana.core.indexation.consumer.message.invitation.organization.InvitationOrganizationUuidAwareActionQueueMessage
import com.vntana.core.indexation.producer.AbstractIndexationProducerUnitTest
import com.vntana.core.indexation.producer.invitation.organization.impl.InvitationOrganizationUuidAwareActionProducerImpl
import org.easymock.Mock
import org.junit.Before
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 6:26 PM
 */
abstract class AbstractInvitationOrganizationUuidAwareActionProducerUnitTest : AbstractIndexationProducerUnitTest() {
    protected lateinit var invitationOrganizationUuidAwareActionProducer: InvitationOrganizationUuidAwareActionProducer

    @Mock
    protected lateinit var kafkaTemplate: KafkaTemplate<String, InvitationOrganizationUuidAwareActionQueueMessage>

    @Mock
    protected lateinit var listenableFuture: ListenableFuture<SendResult<String, InvitationOrganizationUuidAwareActionQueueMessage>>

    @Mock
    protected lateinit var sendResult: SendResult<String, InvitationOrganizationUuidAwareActionQueueMessage>

    protected val preIndexationInvitationOrganizationTopic = uuid()

    @Before
    fun prepare() {
        invitationOrganizationUuidAwareActionProducer = InvitationOrganizationUuidAwareActionProducerImpl(
                preIndexationInvitationOrganizationTopic,
                kafkaTemplate
        )
    }
}