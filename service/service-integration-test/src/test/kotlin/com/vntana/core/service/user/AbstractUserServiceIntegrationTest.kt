package com.vntana.core.service.user

import com.vntana.core.helper.integration.user.UserServiceIntegrationTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 2:26 PM
 */
abstract class AbstractUserServiceIntegrationTest : AbstractServiceIntegrationTest() {
    protected val commonTestHelper: UserCommonTestHelper = UserCommonTestHelper()

    @Autowired
    protected lateinit var integrationTestHelper: UserServiceIntegrationTestHelper

    @Autowired
    protected lateinit var userService: UserService
}