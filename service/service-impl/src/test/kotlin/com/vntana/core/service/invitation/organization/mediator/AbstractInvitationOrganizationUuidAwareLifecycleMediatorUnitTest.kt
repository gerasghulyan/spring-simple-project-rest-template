package com.vntana.core.service.invitation.organization.mediator

import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.invitation.organization.mediator.impl.InvitationOrganizationUuidAwareLifecycleMediatorImpl
import org.easymock.Mock
import org.junit.Before
import org.springframework.context.ApplicationEventPublisher

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 6:30 PM
 */
abstract class AbstractInvitationOrganizationUuidAwareLifecycleMediatorUnitTest : AbstractServiceUnitTest() {
    protected lateinit var invitationOrganizationUuidAwareLifecycleMediator: InvitationOrganizationUuidAwareLifecycleMediator

    protected val helper = InvitationOrganizationCommonTestHelper()

    @Mock
    protected lateinit var applicationEventPublisher: ApplicationEventPublisher

    @Before
    fun prepare() {
        invitationOrganizationUuidAwareLifecycleMediator = InvitationOrganizationUuidAwareLifecycleMediatorImpl(applicationEventPublisher)
    }
}