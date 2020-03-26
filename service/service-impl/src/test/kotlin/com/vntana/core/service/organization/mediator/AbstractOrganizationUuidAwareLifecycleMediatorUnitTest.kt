package com.vntana.core.service.organization.mediator

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.organization.mediator.impl.OrganizationUuidAwareLifecycleMediatorImpl
import org.easymock.Mock
import org.junit.Before
import org.springframework.context.ApplicationEventPublisher

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 2:59 PM
 */
abstract class AbstractOrganizationUuidAwareLifecycleMediatorUnitTest : AbstractServiceUnitTest() {
    protected lateinit var organizationUuidAwareLifecycleMediator: OrganizationUuidAwareLifecycleMediator

    protected val helper: OrganizationCommonTestHelper = OrganizationCommonTestHelper()

    @Mock
    protected lateinit var applicationEventPublisher: ApplicationEventPublisher

    @Before
    fun prepare() {
        organizationUuidAwareLifecycleMediator = OrganizationUuidAwareLifecycleMediatorImpl(applicationEventPublisher)
    }
}