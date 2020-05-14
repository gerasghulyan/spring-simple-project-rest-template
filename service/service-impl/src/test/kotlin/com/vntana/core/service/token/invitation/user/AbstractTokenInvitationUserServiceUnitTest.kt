package com.vntana.core.service.token.invitation.user

import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.persistence.token.TokenInvitationUserRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.invitation.user.InvitationUserService
import com.vntana.core.service.token.invitation.user.impl.TokenInvitationUserServiceImpl
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:27 PM
 */
abstract class AbstractTokenInvitationUserServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var tokenInvitationUserService: TokenInvitationUserService

    @Mock
    protected lateinit var tokenRepository: TokenInvitationUserRepository

    @Mock
    protected lateinit var invitationUserService: InvitationUserService

    protected val commonTestHelper = TokenCommonTestHelper()

    protected val invitationUserCommonTestHelper = InvitationUserCommonTestHelper()

    @Before
    fun before() {
        tokenInvitationUserService = TokenInvitationUserServiceImpl(
                tokenRepository,
                invitationUserService
        )
    }

}