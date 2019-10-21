package com.vntana.core.rest.resource.user

import com.vntana.core.helper.rest.user.UserRestTestHelper
import com.vntana.core.rest.client.user.UserResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:24 AM
 */
abstract class AbstractUserWebTest : AbstractWebIntegrationTest() {
    protected val restHelper: UserRestTestHelper = UserRestTestHelper()

    @Autowired
    protected lateinit var userResourceClient: UserResourceClient
}