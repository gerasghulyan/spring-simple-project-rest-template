package com.vntana.core.rest.facade.token.auth

import com.vntana.core.helper.token.auth.TokenAuthenticationRestTestHelper
import com.vntana.core.helper.unit.token.auth.TokenAuthenticationCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.token.auth.component.precondition.TokenAuthenticationServiceFacadePreconditionCheckerComponent
import com.vntana.core.rest.facade.token.auth.impl.TokenAuthenticationServiceFacadeImpl
import com.vntana.core.service.token.auth.TokenAuthenticationService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:19 PM
 */
abstract class AbstractTokenAuthenticationServiceFacadeUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var tokenAuthenticationServiceFacade: TokenAuthenticationServiceFacade

    @Mock
    protected lateinit var tokenAuthenticationService: TokenAuthenticationService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var preconditionCheckerComponent: TokenAuthenticationServiceFacadePreconditionCheckerComponent

    protected val restTestHelper = TokenAuthenticationRestTestHelper()
    protected val commonTestHelper = TokenAuthenticationCommonTestHelper()

    @Before
    fun prepare() {
        tokenAuthenticationServiceFacade = TokenAuthenticationServiceFacadeImpl(tokenAuthenticationService, userService, preconditionCheckerComponent)
    }
}