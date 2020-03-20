package com.vntana.core.service.token.auth

import com.vntana.core.helper.unit.token.auth.AuthTokenCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.token.auth.AuthTokenRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.token.auth.impl.AuthTokenServiceImpl
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:27 PM
 */
abstract class AbstractAuthTokenServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var authTokenService: AuthTokenService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var authTokenRepository: AuthTokenRepository

    protected val commonTestHelper = AuthTokenCommonTestHelper()
    protected val userCommonTestHelper = UserCommonTestHelper()

    @Before
    fun prepare() {
        authTokenService = AuthTokenServiceImpl(userService, authTokenRepository)
    }
}