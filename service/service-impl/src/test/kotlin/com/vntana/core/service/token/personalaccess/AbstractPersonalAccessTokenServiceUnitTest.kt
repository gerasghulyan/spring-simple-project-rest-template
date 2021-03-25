package com.vntana.core.service.token.personalaccess

import com.vntana.core.helper.unit.token.personalaccess.TokenPersonalAccessCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.token.personalaccess.TokenPersonalAccessRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 12:30 AM
 */
abstract class AbstractPersonalAccessTokenServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var personalAccessTokenService: PersonalAccessTokenService

    @Mock
    protected lateinit var tokenPersonalAccessRepository: TokenPersonalAccessRepository

    @Mock
    protected lateinit var userService: UserService

    protected val userCommonTestHelper = UserCommonTestHelper()

    protected val commonTestHelper = TokenPersonalAccessCommonTestHelper()

    @Before
    fun before() {
        personalAccessTokenService = PersonalAccessTokenServiceImpl(tokenPersonalAccessRepository, userService)
    }
}