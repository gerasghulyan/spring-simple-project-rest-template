package com.vntana.core.rest.facade.token

import com.vntana.core.helper.token.TokenRestTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.rest.facade.token.impl.TokenServiceFacadeImpl
import com.vntana.core.service.token.TokenService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 12:17 PM
 */
abstract class AbstractTokenFacadeUnitTest : AbstractServiceFacadeUnitTest() {

    protected lateinit var tokenServiceFacade: TokenServiceFacade

    @Mock
    protected lateinit var tokenService: TokenService

    @Mock
    protected lateinit var preconditionChecker: TokeFacadePreconditionChecker

    protected val restTestHelper = TokenRestTestHelper()
    protected val commonTestHelper = TokenCommonTestHelper()

    @Before
    fun prepare() {
        tokenServiceFacade = TokenServiceFacadeImpl(tokenService, preconditionChecker)
    }
}