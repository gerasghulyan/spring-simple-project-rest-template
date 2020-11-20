package com.vntana.core.service.invitation.user

import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.invitation.user.InvitationOrganizationClientUserRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.client.OrganizationClientService
import com.vntana.core.service.invitation.user.impl.InvitationUserToClientServiceImpl
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 5:11 PM
 */
abstract class AbstractUserInvitationToClientServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var invitationUserToClientService: InvitationUserToClientService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var organizationClientService: OrganizationClientService

    @Mock
    protected lateinit var invitationOrganizationClientUserRepository: InvitationOrganizationClientUserRepository

    protected val commonTestHelper = InvitationUserCommonTestHelper()

    protected val userCommonTestHelper = UserCommonTestHelper()

    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()

    protected val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()


    @Before
    fun prepare() {
        invitationUserToClientService =
                InvitationUserToClientServiceImpl(userService, organizationClientService, invitationOrganizationClientUserRepository)
    }
}