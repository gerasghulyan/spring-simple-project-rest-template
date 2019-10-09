package com.vntana.core.rest.resource.user

import com.vntana.core.helper.rest.UserRestTestHelper
import com.vntana.core.rest.resource.AbstractWebIntegrationTest

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:24 AM
 */
abstract class AbstractUserWebTest : AbstractWebIntegrationTest() {
    protected val userTestHelper: UserRestTestHelper = UserRestTestHelper()

    override fun baseMapping(): String = "http://localhost:${port}/users/"
}