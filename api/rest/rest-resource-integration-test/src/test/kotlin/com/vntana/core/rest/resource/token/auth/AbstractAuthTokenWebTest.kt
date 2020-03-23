package com.vntana.core.rest.resource.token.auth

import com.vntana.core.helper.token.auth.AuthTokenResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.rest.client.token.auth.AuthTokenResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:56 AM
 */
abstract class AbstractAuthTokenWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var authTokenResourceClient: AuthTokenResourceClient

    @Autowired
    protected lateinit var resourceTestHelper: AuthTokenResourceTestHelper

    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper
}