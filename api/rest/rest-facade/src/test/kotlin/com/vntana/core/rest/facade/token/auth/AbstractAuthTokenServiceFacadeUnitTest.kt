package com.vntana.core.rest.facade.token.auth

import com.vntana.core.helper.token.auth.AuthTokenRestTestHelper
import com.vntana.core.helper.unit.token.auth.AuthTokenCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.token.auth.impl.AuthTokenServiceFacadeImpl
import com.vntana.core.service.token.auth.AuthTokenService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:19 PM
 */
abstract class AbstractAuthTokenServiceFacadeUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var authTokenServiceFacade: AuthTokenServiceFacade

    @Mock
    protected lateinit var authTokenService: AuthTokenService

    @Mock
    protected lateinit var userService: UserService

    protected val restTestHelper = AuthTokenRestTestHelper()
    protected val commonTestHelper = AuthTokenCommonTestHelper()

    @Before
    fun prepare() {
        authTokenServiceFacade = AuthTokenServiceFacadeImpl(authTokenService, userService)
    }
}