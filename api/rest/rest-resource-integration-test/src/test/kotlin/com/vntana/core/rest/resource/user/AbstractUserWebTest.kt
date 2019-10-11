package com.vntana.core.rest.resource.user

import com.vntana.core.helper.rest.user.UserRestTestHelper
import com.vntana.core.rest.resource.AbstractWebIntegrationTest

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:24 AM
 */
abstract class AbstractUserWebTest : AbstractWebIntegrationTest() {
    protected val restHelper: UserRestTestHelper = UserRestTestHelper()

    override fun baseMapping(): String = "http://localhost:${port}/users/"
}