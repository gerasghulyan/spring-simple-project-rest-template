package com.vntana.core.service.organization.mediator.impl

import com.vntana.core.listener.commons.EntityLifecycle
import com.vntana.core.listener.organization.OrganizationLifecyclePayload
import com.vntana.core.service.organization.mediator.AbstractOrganizationLifecycleMediatorUnitTest
import org.assertj.core.api.Assertions
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 12/9/19
 * Time: 5:55 PM
 */
class OrganizationLifecycleMediatorImplOnCreateUnitTest: AbstractOrganizationLifecycleMediatorUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        Assertions.assertThatThrownBy { organizationLifecycleMediator.onCreated(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when absent in locations and products`() {
        val organization = helper.buildOrganization()
        resetAll()
        expect(applicationEventPublisher.publishEvent(OrganizationLifecyclePayload(organization, EntityLifecycle.CREATED))).andVoid()
        replayAll()
        organizationLifecycleMediator.onCreated(organization)
        verifyAll()
    }
}