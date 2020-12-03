package com.vntana.core.service.token.reset.password

import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.token.reset_password.TokenResetPasswordRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Geras Ghulyan
 * Date: 6/23/20
 * Time: 1:13 PM
 */
abstract class AbstractTokenResetPasswordServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var tokenResetPasswordService: TokenResetPasswordService

    @Mock
    protected lateinit var tokenResetPasswordRepository: TokenResetPasswordRepository

    @Mock
    protected lateinit var userService: UserService

    protected val commonTestHelper = TokenCommonTestHelper()

    protected val userCommonTestHelper = UserCommonTestHelper()

    @Before
    fun before() {
        tokenResetPasswordService = TokenResetPasswordServiceImpl(
                userService,
                tokenResetPasswordRepository
        )
    }
}