package com.vntana.core.service.client.mediator.impl

import com.vntana.core.listener.client.ClientOrganizationLifecyclePayload
import com.vntana.core.listener.commons.EntityLifecycle
import com.vntana.core.service.client.mediator.AbstractClientOrganizationLifecycleMediatorUnitTest
import org.assertj.core.api.Assertions
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 12/9/19
 * Time: 5:55 PM
 */
class ClientOrganizationLifecycleMediatorImplOnCreateUnitTest : AbstractClientOrganizationLifecycleMediatorUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        Assertions.assertThatThrownBy { clientOrganizationLifecycleMediator.onCreated(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when absent in locations and products`() {
        val client = clientOrganizationCommonTestHelper.buildClientOrganization()
        resetAll()
        expect(applicationEventPublisher.publishEvent(ClientOrganizationLifecyclePayload(client, EntityLifecycle.CREATED))).andVoid()
        replayAll()
        clientOrganizationLifecycleMediator.onCreated(client)
        verifyAll()
    }
}