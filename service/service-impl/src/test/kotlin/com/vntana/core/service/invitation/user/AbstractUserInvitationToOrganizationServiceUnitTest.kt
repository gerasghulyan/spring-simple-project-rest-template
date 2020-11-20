package com.vntana.core.service.invitation.user

import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.invitation.user.InvitationOrganizationUserRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.invitation.user.impl.InvitationUserToOrganizationServiceImpl
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 3:56 PM
 */
abstract class AbstractUserInvitationToOrganizationServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var invitationUserToOrganizationService: InvitationUserToOrganizationService

    @Mock
    protected lateinit var invitationOrganizationUserRepository: InvitationOrganizationUserRepository

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var organizationService: OrganizationService

    protected val commonTestHelper = InvitationUserCommonTestHelper()

    protected val userCommonTestHelper = UserCommonTestHelper()

    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()

    protected val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    @Before
    fun prepare() {
        invitationUserToOrganizationService =
                InvitationUserToOrganizationServiceImpl(invitationOrganizationUserRepository, userService, organizationService)
    }
}