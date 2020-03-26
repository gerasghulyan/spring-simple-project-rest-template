package com.vntana.core.indexation.producer.organization

import com.vntana.core.indexation.consumer.message.organization.OrganizationUuidAwareActionQueueMessage
import com.vntana.core.indexation.producer.AbstractIndexationProducerUnitTest
import com.vntana.core.indexation.producer.organization.impl.OrganizationUuidAwareActionProducerImpl
import org.easymock.Mock
import org.junit.Before
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 3:11 PM
 */
abstract class AbstractOrganizationUuidAwareActionProducerUnitTest : AbstractIndexationProducerUnitTest() {
    protected lateinit var organizationUuidAwareActionProducer: OrganizationUuidAwareActionProducer

    @Mock
    protected lateinit var kafkaTemplate: KafkaTemplate<String, OrganizationUuidAwareActionQueueMessage>

    @Mock
    protected lateinit var listenableFuture: ListenableFuture<SendResult<String, OrganizationUuidAwareActionQueueMessage>>

    @Mock
    protected lateinit var sendResult: SendResult<String, OrganizationUuidAwareActionQueueMessage>

    protected val preIndexationOrganizationTopic = uuid()

    @Before
    fun prepare() {
        organizationUuidAwareActionProducer = OrganizationUuidAwareActionProducerImpl(preIndexationOrganizationTopic, kafkaTemplate)
    }
}