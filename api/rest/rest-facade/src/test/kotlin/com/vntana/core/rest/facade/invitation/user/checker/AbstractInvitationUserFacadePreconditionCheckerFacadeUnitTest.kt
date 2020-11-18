package com.vntana.core.rest.facade.invitation.user.checker

import com.vntana.core.helper.invitation.user.InvitationUserRestTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.rest.facade.invitation.user.checker.impl.InvitationUserFacadePreconditionCheckerImpl
import com.vntana.core.rest.facade.invitation.user.component.UserRolesPermissionsCheckerComponent
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.client.OrganizationClientService
import com.vntana.core.service.invitation.user.InvitationUserService
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.token.invitation.user.TokenInvitationUserService
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.role.UserRoleService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 5:52 PM
 */
abstract class AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest : AbstractFacadeUnitTest() {
    protected lateinit var preconditionChecker: InvitationUserFacadePreconditionChecker

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var userRoleService: UserRoleService

    @Mock
    protected lateinit var organizationService: OrganizationService
    
    @Mock
    protected lateinit var organizationClientService: OrganizationClientService

    @Mock
    protected lateinit var invitationUserService: InvitationUserService

    @Mock
    protected lateinit var tokenInvitationUserService: TokenInvitationUserService
    
    @Mock
    protected lateinit var userRolesPermissionsCheckerComponent: UserRolesPermissionsCheckerComponent

    protected val tokenCommonTestHelper = TokenCommonTestHelper()

    protected val invitationUserRestTestHelper = InvitationUserRestTestHelper()

    protected val userCommonTestHelper = UserCommonTestHelper()

    protected val userRoleCommonTestHelper = UserRoleCommonTestHelper()

    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()
    
    protected val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    @Before
    fun prepare() {
        preconditionChecker = InvitationUserFacadePreconditionCheckerImpl(userService,
                userRoleService,
                organizationService,
                invitationUserService,
                tokenInvitationUserService,
                organizationClientService,
                userRolesPermissionsCheckerComponent
        )
    }
}