package com.vntana.core.service.organization.mediator

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.organization.mediator.impl.OrganizationLifecycleMediatorImpl
import org.easymock.Mock
import org.junit.Before
import org.springframework.context.ApplicationEventPublisher

/**
 * Created by Geras Ghulyan.
 * Date: 11/1/19
 * Time: 4:33 PM
 */
abstract class AbstractOrganizationLifecycleMediatorUnitTest : AbstractServiceUnitTest() {

    protected lateinit var organizationLifecycleMediator: OrganizationLifecycleMediator

    protected val helper: OrganizationCommonTestHelper = OrganizationCommonTestHelper()

    @Mock
    protected lateinit var applicationEventPublisher: ApplicationEventPublisher

    @Before
    fun prepare() {
        organizationLifecycleMediator = OrganizationLifecycleMediatorImpl(
                applicationEventPublisher
        )
    }
}