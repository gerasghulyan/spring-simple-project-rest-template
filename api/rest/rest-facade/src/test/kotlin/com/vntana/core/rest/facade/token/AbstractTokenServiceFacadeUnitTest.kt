package com.vntana.core.rest.facade.token

import com.vntana.core.helper.rest.token.TokenRestTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.rest.facade.token.impl.ResetPasswordTokenServiceFacadeImpl
import com.vntana.core.service.token.ResetPasswordTokenService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:30 PM
 */
abstract class AbstractTokenServiceFacadeUnitTest : AbstractServiceFacadeUnitTest() {

    protected lateinit var resetPasswordTokenServiceFacade: ResetPasswordTokenServiceFacade

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var resetPasswordTokenService: ResetPasswordTokenService

    protected val restTestHelper = TokenRestTestHelper()

    protected val unitTestHelper = TokenCommonTestHelper()

    protected val userUnitTestHelper = UserCommonTestHelper()

    @Before
    fun before() {
        resetPasswordTokenServiceFacade = ResetPasswordTokenServiceFacadeImpl(userService, resetPasswordTokenService)
    }

}