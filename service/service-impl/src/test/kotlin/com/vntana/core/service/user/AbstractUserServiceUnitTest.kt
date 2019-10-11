package com.vntana.core.service.user

import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.user.UserRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.client.ClientOrganizationService
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

    @Mock
    protected lateinit var clientOrganizationService: ClientOrganizationService

    protected lateinit var userService: UserService

    protected val helper = UserCommonTestHelper()

    protected val clientOrganizationHelper = ClientOrganizationCommonTestHelper()

    @Before
    fun beforeTest() {
        userService = UserServiceImpl(userRepository, clientOrganizationService)
    }
}