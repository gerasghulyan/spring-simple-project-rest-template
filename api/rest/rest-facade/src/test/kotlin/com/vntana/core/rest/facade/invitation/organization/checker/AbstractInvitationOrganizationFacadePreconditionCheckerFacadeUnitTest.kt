package com.vntana.core.rest.facade.invitation.organization.checker

import com.vntana.core.helper.invitation.organization.InvitationOrganizationRestTestHelper
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.helper.unit.token.invitation.organization.TokenInvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.rest.facade.invitation.organization.checker.impl.InvitationOrganizationFacadePreconditionCheckerImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.common.component.SlugValidationComponent
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 3:03 PM
 */
abstract class AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var preconditionChecker: InvitationOrganizationFacadePreconditionChecker

    @Mock
    protected lateinit var invitationOrganizationService: InvitationOrganizationService

    @Mock
    protected lateinit var slugValidationComponent: SlugValidationComponent

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var tokenInvitationOrganizationService: TokenInvitationOrganizationService

    protected val restTestHelper = InvitationOrganizationRestTestHelper()
    protected val invitationOrganizationCommonTestHelper = InvitationOrganizationCommonTestHelper()
    protected val tokenCommonHelper = TokenCommonTestHelper()
    protected val tokenInvitationOrganizationCommonTestHelper = TokenInvitationOrganizationCommonTestHelper()
    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()
    protected val userCommonTestHelper = UserCommonTestHelper()

    @Before
    fun prepare() {
        preconditionChecker = InvitationOrganizationFacadePreconditionCheckerImpl(invitationOrganizationService,
                slugValidationComponent,
                organizationService,
                userService,
                tokenInvitationOrganizationService
        )
    }
}