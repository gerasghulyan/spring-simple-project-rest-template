package com.vntana.core.service.organization.mediator.impl

import com.vntana.commons.indexation.payload.EntityLifecycle
import com.vntana.core.listener.organization.OrganizationUuidAwareLifecyclePayload
import com.vntana.core.service.organization.mediator.AbstractOrganizationUuidAwareLifecycleMediatorUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 3:00 PM
 */
class OrganizationUuidAwareLifecycleMediatorUnitTest : AbstractOrganizationUuidAwareLifecycleMediatorUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { organizationUuidAwareLifecycleMediator.onCreated(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationUuidAwareLifecycleMediator.onUpdated(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationUuidAwareLifecycleMediator.onDeleted(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test onCreated`() {
        val uuid = uuid()
        val payload = OrganizationUuidAwareLifecyclePayload(uuid, EntityLifecycle.CREATED)
        resetAll()
        expect(applicationEventPublisher.publishEvent(payload)).andVoid()
        replayAll()
        organizationUuidAwareLifecycleMediator.onCreated(uuid)
        verifyAll()
    }

    @Test
    fun `test onUpdated`() {
        val uuid = uuid()
        val payload = OrganizationUuidAwareLifecyclePayload(uuid, EntityLifecycle.UPDATED)
        resetAll()
        expect(applicationEventPublisher.publishEvent(payload)).andVoid()
        replayAll()
        organizationUuidAwareLifecycleMediator.onUpdated(uuid)
        verifyAll()
    }

    @Test
    fun `test onDeleted`() {
        val uuid = uuid()
        val payload = OrganizationUuidAwareLifecyclePayload(uuid, EntityLifecycle.DELETED)
        resetAll()
        expect(applicationEventPublisher.publishEvent(payload)).andVoid()
        replayAll()
        organizationUuidAwareLifecycleMediator.onDeleted(uuid)
        verifyAll()
    }
}