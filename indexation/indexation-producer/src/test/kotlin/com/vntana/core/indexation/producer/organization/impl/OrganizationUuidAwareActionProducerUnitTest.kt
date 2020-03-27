package com.vntana.core.indexation.producer.organization.impl

import com.vntana.commons.queue.model.MessageActionType
import com.vntana.core.indexation.consumer.message.organization.OrganizationUuidAwareActionQueueMessage
import com.vntana.core.indexation.producer.organization.AbstractOrganizationUuidAwareActionProducerUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 3:13 PM
 */
class OrganizationUuidAwareActionProducerUnitTest : AbstractOrganizationUuidAwareActionProducerUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { organizationUuidAwareActionProducer.produce(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test produce`() {
        // test data
        val message = OrganizationUuidAwareActionQueueMessage(MessageActionType.CREATED, uuid())
        resetAll()
        // expectations
        expect(kafkaTemplate.send(preIndexationOrganizationTopic, message.uuid, message)).andReturn(listenableFuture)
        expect(listenableFuture.get(5000L, TimeUnit.MILLISECONDS)).andReturn(sendResult)
        replayAll()
        // test scenario
        organizationUuidAwareActionProducer.produce(message)
        verifyAll()
    }

    @Test
    fun `test produce failed`() {
        // test data
        val message = OrganizationUuidAwareActionQueueMessage(MessageActionType.CREATED, uuid())
        resetAll()
        // expectations
        expect(kafkaTemplate.send(preIndexationOrganizationTopic, message.uuid, message)).andReturn(listenableFuture)
        expect(listenableFuture.get(5000L, TimeUnit.MILLISECONDS)).andThrow(TimeoutException())
        replayAll()
        // test scenario
        assertThatThrownBy { organizationUuidAwareActionProducer.produce(message) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }
}