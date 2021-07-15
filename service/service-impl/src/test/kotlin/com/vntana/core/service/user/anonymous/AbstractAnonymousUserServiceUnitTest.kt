package com.vntana.core.service.user.anonymous

import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.user.UserRepository
import com.vntana.core.persistence.user.anonymous.AnonymousUserRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.user.anonymous.impl.AnonymousUserServiceImpl
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 3:11 PM
 */
abstract class AbstractAnonymousUserServiceUnitTest : AbstractServiceUnitTest() {

    @Mock
    protected lateinit var repository: AnonymousUserRepository

    @Mock
    protected lateinit var userRepository: UserRepository

    protected lateinit var anonymousUserService: AnonymousUserService

    protected val helper = UserCommonTestHelper()

    @Before
    fun beforeTest() {
        anonymousUserService = AnonymousUserServiceImpl(repository, userRepository)
    }
}