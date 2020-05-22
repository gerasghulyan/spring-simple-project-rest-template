package com.vntana.core.service.user

import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 2:26 PM
 */
abstract class AbstractUserServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var integrationTestHelper: UserIntegrationTestHelper

    @Autowired
    protected lateinit var organizationIntegrationTest: OrganizationIntegrationTestHelper

    @Autowired
    protected lateinit var userService: UserService

    @Autowired
    protected lateinit var passwordEncoder: BCryptPasswordEncoder
}