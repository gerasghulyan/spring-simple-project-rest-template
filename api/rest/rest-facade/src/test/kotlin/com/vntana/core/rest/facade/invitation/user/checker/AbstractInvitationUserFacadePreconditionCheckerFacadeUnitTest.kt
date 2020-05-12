package com.vntana.core.rest.facade.invitation.user.checker

import com.vntana.core.helper.invitation.user.InvitationUserRestTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.rest.facade.invitation.user.checker.impl.InvitationUserFacadePreconditionCheckerImpl
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 5:52 PM
 */
abstract class AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest : AbstractServiceFacadeUnitTest() {
    protected lateinit var preconditionChecker: InvitationUserFacadePreconditionChecker

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var organizationService: OrganizationService

    protected val invitationUserRestTestHelper = InvitationUserRestTestHelper()

    protected val userCommonTestHelper = UserCommonTestHelper()

    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()

    @Before
    fun prepare() {
        preconditionChecker = InvitationUserFacadePreconditionCheckerImpl(userService, organizationService)
    }
}