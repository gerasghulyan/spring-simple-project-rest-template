package com.vntana.core.service.token

import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.token.ResetPasswordTokenRepository
import com.vntana.core.persistence.token.TokenRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import com.vntana.core.service.token.impl.ResetPasswordTokenServiceImpl
import com.vntana.core.service.token.impl.TokenServiceImpl
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 1:06 PM
 */
abstract class AbstractTokenServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var tokenService: TokenService

    protected lateinit var resetPasswordTokenService: ResetPasswordTokenService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var invitationOrganizationService: InvitationOrganizationService

    @Mock
    protected lateinit var tokenRepository: TokenRepository

    @Mock
    protected lateinit var resetPasswordTokenRepository: ResetPasswordTokenRepository

    protected val helper = TokenCommonTestHelper()

    protected val userHelper = UserCommonTestHelper()

    protected val invitationOrganizationCommonTestHelper = InvitationOrganizationCommonTestHelper()

    @Before
    fun before() {
        tokenService = TokenServiceImpl(invitationOrganizationService, tokenRepository)
        resetPasswordTokenService = ResetPasswordTokenServiceImpl(userService, resetPasswordTokenRepository)
    }

}