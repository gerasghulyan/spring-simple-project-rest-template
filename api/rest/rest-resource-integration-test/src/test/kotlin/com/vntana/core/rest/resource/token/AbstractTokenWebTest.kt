package com.vntana.core.rest.resource.token

import com.vntana.core.helper.client.ClientOrganizationResourceTestHelper
import com.vntana.core.helper.invitation.organization.InvitationOrganizationResourceTestHelper
import com.vntana.core.helper.invitation.user.InvitationUserResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.token.TokenResourceTestHelper
import com.vntana.core.helper.token.TokenRestTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.rest.client.token.TokenResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 2:25 PM
 */
abstract class AbstractTokenWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var tokenResourceClient: TokenResourceClient

    @Autowired
    protected lateinit var resourceTestHelper: TokenRestTestHelper

    @Autowired
    protected lateinit var tokenResourceTestHelper: TokenResourceTestHelper

    @Autowired
    protected lateinit var invitationOrganizationResourceTestHelper: InvitationOrganizationResourceTestHelper

    @Autowired
    protected lateinit var invitationUserResourceTestHelper: InvitationUserResourceTestHelper

    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper

    @Autowired
    protected lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    @Autowired
    protected lateinit var clientOrganizationResourceTestHelper: ClientOrganizationResourceTestHelper
}