package com.vntana.core.rest.facade.token.personalaccess

import com.vntana.core.helper.unit.token.personalaccess.TokenPersonalAccessCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.token.personalaccess.impl.PersonalAccessTokenServiceFacadeImpl
import com.vntana.core.service.token.personalaccess.PersonalAccessTokenService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 4:57 PM
 */
open class AbstractPersonalAccessTokenFacadeUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var personalAccessTokenServiceFacade: PersonalAccessTokenServiceFacade

    @Mock
    protected lateinit var personalAccessTokenService: PersonalAccessTokenService

    @Mock
    protected lateinit var userService: UserService

    protected val userHelper = UserCommonTestHelper()
    
    protected val testHelper = TokenPersonalAccessCommonTestHelper()

    @Before
    fun prepare() {
        personalAccessTokenServiceFacade = PersonalAccessTokenServiceFacadeImpl(
            personalAccessTokenService,
            userService
        )
    }
}