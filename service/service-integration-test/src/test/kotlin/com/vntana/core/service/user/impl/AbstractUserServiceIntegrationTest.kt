package com.vntana.core.service.user.impl

import com.vntana.core.helper.integration.UserIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import com.vntana.core.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 2:26 PM
 */
abstract class AbstractUserServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    lateinit var testHelper: UserIntegrationTestHelper

    @Autowired
    lateinit var userService: UserService
}