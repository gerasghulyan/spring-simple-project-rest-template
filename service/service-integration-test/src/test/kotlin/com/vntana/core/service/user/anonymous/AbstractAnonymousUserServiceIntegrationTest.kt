package com.vntana.core.service.user.anonymous

import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.persistence.user.anonymous.AnonymousUserRepository
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 3:59 PM
 */
abstract class AbstractAnonymousUserServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var integrationTestHelper: UserIntegrationTestHelper

    @Autowired
    protected lateinit var anonymousUserService: AnonymousUserService
}