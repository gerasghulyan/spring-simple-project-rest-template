package com.vntana.core.rest.facade.invitation.user

import com.vntana.core.helper.invitation.user.InvitationUserRestTestHelper
import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.rest.facade.invitation.user.checker.InvitationUserFacadePreconditionChecker
import com.vntana.core.rest.facade.invitation.user.component.InvitationUserSenderComponent
import com.vntana.core.rest.facade.invitation.user.impl.InvitationUserServiceFacadeImpl
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.invitation.user.InvitationUserService
import ma.glasnost.orika.MapperFacade
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 3:35 PM
 */
abstract class AbstractInvitationUserFacadeUnitTest : AbstractServiceFacadeUnitTest() {
    protected lateinit var invitationUserServiceFacade: InvitationUserServiceFacade

    @Mock
    protected lateinit var preconditionChecker: InvitationUserFacadePreconditionChecker

    @Mock
    protected lateinit var invitationUserService: InvitationUserService

    @Mock
    protected lateinit var mapperFacade: MapperFacade
    
    @Mock
    protected lateinit var invitationUserSenderComponent: InvitationUserSenderComponent

    protected val invitationUserRestTestHelper = InvitationUserRestTestHelper()

    protected val invitationUserCommonTestHelper = InvitationUserCommonTestHelper()

    protected val userCommonTestHelper = UserCommonTestHelper()

    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()

    @Before
    fun prepare() {
        invitationUserServiceFacade = InvitationUserServiceFacadeImpl(invitationUserService, preconditionChecker, mapperFacade, invitationUserSenderComponent)
    }
}