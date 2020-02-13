package com.vntana.core.service.client.mediator.impl

import com.vntana.core.service.client.mediator.AbstractClientOrganizationLifecycleMediatorUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 2/13/20
 * Time: 3:18 PM
 */
class ClientOrganizationLifecycleMediatorOnDeleteUnitTest : AbstractClientOrganizationLifecycleMediatorUnitTest() {

    @Test
    fun test() {
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        resetAll()
        replayAll()
        assertThatThrownBy { clientOrganizationLifecycleMediator.onDeleted(clientOrganization) }
                .isExactlyInstanceOf(UnsupportedOperationException::class.java)
        verifyAll()
    }
}