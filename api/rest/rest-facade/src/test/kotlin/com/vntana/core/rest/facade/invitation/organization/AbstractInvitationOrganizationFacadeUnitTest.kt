package com.vntana.core.rest.facade.invitation.organization

import com.vntana.core.helper.invitation.organization.InvitationOrganizationRestTestHelper
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.token.invitation.organization.TokenInvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.persistence.utils.PersistenceUtilityService
import com.vntana.core.rest.facade.invitation.organization.checker.InvitationOrganizationFacadePreconditionChecker
import com.vntana.core.rest.facade.invitation.organization.component.InvitationOrganizationSenderComponent
import com.vntana.core.rest.facade.invitation.organization.impl.InvitationOrganizationServiceFacadeImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import com.vntana.core.service.invitation.organization.mediator.InvitationOrganizationUuidAwareLifecycleMediator
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.organization.mediator.OrganizationLifecycleMediator
import com.vntana.core.service.organization.mediator.OrganizationUuidAwareLifecycleMediator
import com.vntana.core.service.token.TokenService
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.role.UserRoleService
import ma.glasnost.orika.MapperFacade
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 3:35 PM
 */
abstract class AbstractInvitationOrganizationFacadeUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var invitationOrganizationServiceFacade: InvitationOrganizationServiceFacade

    @Mock
    protected lateinit var invitationOrganizationService: InvitationOrganizationService

    @Mock
    protected lateinit var tokenService: TokenService

    @Mock
    protected lateinit var preconditionChecker: InvitationOrganizationFacadePreconditionChecker

    @Mock
    protected lateinit var invitationOrganizationUuidAwareLifecycleMediator: InvitationOrganizationUuidAwareLifecycleMediator

    @Mock
    protected lateinit var invitationOrganizationSenderComponent: InvitationOrganizationSenderComponent

    @Mock
    protected lateinit var mapperFacade: MapperFacade

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var persistenceUtilityService: PersistenceUtilityService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var userRoleService: UserRoleService

    @Mock
    protected lateinit var organizationLifecycleMediator: OrganizationLifecycleMediator

    @Mock
    protected lateinit var organizationUuidAwareLifecycleMediator: OrganizationUuidAwareLifecycleMediator

    @Mock
    protected lateinit var tokenInvitationOrganizationService: TokenInvitationOrganizationService

    protected val restTestHelper = InvitationOrganizationRestTestHelper()
    protected val commonTestHelper = InvitationOrganizationCommonTestHelper()
    protected val tokenInvitationOrganizationCommonTestHelper = TokenInvitationOrganizationCommonTestHelper()
    protected val userCommonTestHelper = UserCommonTestHelper()
    protected val userRoleCommonTestHelper = UserRoleCommonTestHelper()
    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()

    @Before
    fun prepare() {
        invitationOrganizationServiceFacade = InvitationOrganizationServiceFacadeImpl(invitationOrganizationService,
                tokenService,
                preconditionChecker,
                invitationOrganizationUuidAwareLifecycleMediator,
                invitationOrganizationSenderComponent,
                mapperFacade,
                organizationService,
                persistenceUtilityService,
                userService,
                userRoleService,
                organizationLifecycleMediator,
                organizationUuidAwareLifecycleMediator,
                tokenInvitationOrganizationService
        )
    }
}