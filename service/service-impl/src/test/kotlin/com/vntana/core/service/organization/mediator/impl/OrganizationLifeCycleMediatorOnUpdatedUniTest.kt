package com.vntana.core.service.organization.mediator.impl

import com.vntana.core.service.organization.mediator.AbstractOrganizationLifecycleMediatorUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 2/13/20
 * Time: 3:04 PM
 */
class OrganizationLifeCycleMediatorOnUpdatedUniTest : AbstractOrganizationLifecycleMediatorUnitTest() {

    @Test
    fun test() {
        val organization = helper.buildOrganization()
        resetAll()
        replayAll()
        assertThatThrownBy { organizationLifecycleMediator.onUpdated(organization) }
                .isExactlyInstanceOf(UnsupportedOperationException::class.java)
        verifyAll()
    }
}