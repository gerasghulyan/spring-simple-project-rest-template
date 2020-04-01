package com.vntana.core.listener.organization

import com.vntana.cache.service.organization.OrganizationLockService
import com.vntana.commons.indexation.payload.EntityLifecycle
import com.vntana.commons.queue.model.MessageActionType
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.indexation.consumer.message.organization.OrganizationUuidAwareActionQueueMessage
import com.vntana.core.indexation.producer.organization.OrganizationUuidAwareActionProducer
import com.vntana.core.listener.AbstractListenerUnitTest
import com.vntana.core.service.organization.OrganizationService
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 2:13 PM
 */
class OrganizationUuidAwareIndexationStartEventListenerUnitTest : AbstractListenerUnitTest() {

    private lateinit var organizationUuidAwareIndexationStartEventListener: OrganizationUuidAwareIndexationStartEventListener

    private val organizationCommonTestHelper: OrganizationCommonTestHelper = OrganizationCommonTestHelper()

    @Mock
    private lateinit var organizationUuidAwareActionProducer: OrganizationUuidAwareActionProducer
    
    @Mock
    private lateinit var organizationLockService: OrganizationLockService

    @Mock
    private lateinit var organizationService: OrganizationService

    @Before
    fun prepare() {
        organizationUuidAwareIndexationStartEventListener = OrganizationUuidAwareIndexationStartEventListener(organizationUuidAwareActionProducer, organizationService, organizationLockService)
    }

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { organizationUuidAwareIndexationStartEventListener.handleEvent(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test handleEvent`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val payload = OrganizationUuidAwareLifecyclePayload(organization.uuid, EntityLifecycle.CREATED)
        val message = OrganizationUuidAwareActionQueueMessage(MessageActionType.CREATED, organization.uuid)
        resetAll()
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        expect(organizationLockService.lock(organization.uuid)).andVoid()
        expect(organizationUuidAwareActionProducer.produce(message)).andVoid()
        replayAll()
        organizationUuidAwareIndexationStartEventListener.handleEvent(payload)
        verifyAll()
    }

    @Test
    fun `test handleEvent update when organization removed`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        organization.removed = LocalDateTime.now()
        val payload = OrganizationUuidAwareLifecyclePayload(organization.uuid, EntityLifecycle.UPDATED)
        resetAll()
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        replayAll()
        organizationUuidAwareIndexationStartEventListener.handleEvent(payload)
        verifyAll()
    }
}