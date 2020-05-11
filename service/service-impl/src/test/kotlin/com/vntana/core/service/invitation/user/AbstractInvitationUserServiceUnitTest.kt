package com.vntana.core.service.invitation.user

import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.invitation.user.InvitationUserRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.invitation.user.impl.InvitationUserServiceImpl
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 3:56 PM
 */
abstract class AbstractInvitationUserServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var invitationUserService: InvitationUserService

    @Mock
    protected lateinit var invitationUserRepository: InvitationUserRepository

    @Mock
    protected lateinit var userService: UserService

    protected val commonTestHelper = InvitationUserCommonTestHelper()

    protected val userCommonTestHelper = UserCommonTestHelper()

    @Before
    fun prepare() {
        invitationUserService = InvitationUserServiceImpl(invitationUserRepository, userService)
    }
}