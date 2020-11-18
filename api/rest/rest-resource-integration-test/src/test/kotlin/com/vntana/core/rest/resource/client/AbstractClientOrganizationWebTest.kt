package com.vntana.core.rest.resource.client

import com.vntana.core.helper.client.ClientOrganizationResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.helper.user.role.UserRoleResourceTestHelper
import com.vntana.core.rest.client.client.ClientOrganizationResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:51 PM
 */
abstract class AbstractClientOrganizationWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper

    @Autowired
    protected lateinit var clientOrganizationResourceTestHelper: ClientOrganizationResourceTestHelper

    @Autowired
    protected lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    @Autowired
    protected lateinit var userRoleResourceTestHelper: UserRoleResourceTestHelper

    @Autowired
    protected lateinit var clientOrganizationResourceClient: ClientOrganizationResourceClient
}