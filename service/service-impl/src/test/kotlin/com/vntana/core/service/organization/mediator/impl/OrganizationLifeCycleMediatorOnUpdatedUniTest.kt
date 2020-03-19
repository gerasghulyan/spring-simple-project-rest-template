package com.vntana.core.service.organization.mediator.impl

import com.vntana.core.listener.commons.EntityLifecycle
import com.vntana.core.listener.organization.OrganizationLifecyclePayload
import com.vntana.core.service.organization.mediator.AbstractOrganizationLifecycleMediatorUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 2/13/20
 * Time: 3:04 PM
 */
class OrganizationLifeCycleMediatorOnUpdatedUniTest : AbstractOrganizationLifecycleMediatorUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { organizationLifecycleMediator.onUpdated(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val organization = helper.buildOrganization()
        resetAll()
        expect(applicationEventPublisher.publishEvent(OrganizationLifecyclePayload(organization, EntityLifecycle.UPDATED))).andVoid()
        replayAll()
        organizationLifecycleMediator.onUpdated(organization)
        verifyAll()
    }
}