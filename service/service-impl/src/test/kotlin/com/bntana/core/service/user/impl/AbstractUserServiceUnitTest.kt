package com.bntana.core.service.user.impl

import com.bntana.core.service.AbstractServiceUnitTest
import com.vntana.core.helper.common.user.UserCommonTestHelper
import com.vntana.core.persistence.user.UserRepository
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.impl.UserServiceImpl
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 11:21 AM
 */
abstract class AbstractUserServiceUnitTest : AbstractServiceUnitTest() {

    @Mock
    protected lateinit var userRepository: UserRepository

    protected lateinit var userService: UserService

    protected val helper: UserCommonTestHelper = UserCommonTestHelper()

    @Before
    fun beforeTest() {
        userService = UserServiceImpl(userRepository)
    }
}