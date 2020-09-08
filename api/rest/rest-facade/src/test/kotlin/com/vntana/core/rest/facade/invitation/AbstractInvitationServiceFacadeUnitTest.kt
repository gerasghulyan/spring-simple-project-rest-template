package com.vntana.core.rest.facade.invitation

import com.vntana.core.helper.invitation.InvitationRestTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.rest.facade.invitation.component.PlatformInvitationSenderComponent
import com.vntana.core.rest.facade.invitation.impl.InvitationServiceFacadeImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 11:11 AM
 */
abstract class AbstractInvitationServiceFacadeUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var invitationServiceFacade: InvitationServiceFacade

    protected val restTestHelper: InvitationRestTestHelper = InvitationRestTestHelper()
    protected val userCommonTestHelper: UserCommonTestHelper = UserCommonTestHelper()

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var platformInvitationSenderComponent: PlatformInvitationSenderComponent

    @Before
    fun prepare() {
        invitationServiceFacade = InvitationServiceFacadeImpl(userService, platformInvitationSenderComponent)
    }
}