package com.vntana.core.rest.resource.auth

import com.vntana.core.helper.client.ClientOrganizationResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.helper.user.role.UserRoleResourceTestHelper
import com.vntana.core.rest.client.auth.AuthResourceClient
import com.vntana.core.rest.client.user.role.UserRoleResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 3/17/20
 * Time: 6:41 PM
 */
abstract class AbstractAuthWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var authResourceClient: AuthResourceClient

    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper

    @Autowired
    protected lateinit var clientOrganizationResourceTestHelper : ClientOrganizationResourceTestHelper

    @Autowired
    protected lateinit var organizationResourceTestHelper : OrganizationResourceTestHelper

    @Autowired
    protected lateinit var userRoleResourceTestHelper: UserRoleResourceTestHelper
}