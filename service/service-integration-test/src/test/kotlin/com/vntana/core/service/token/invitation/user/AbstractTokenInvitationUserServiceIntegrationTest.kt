package com.vntana.core.service.token.invitation.user

import com.vntana.core.helper.integration.client.ClientOrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.invitation.user.InvitationUserIntegrationTestHelper
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.token.invitation.user.TokenInvitationUserIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:53 PM
 */
abstract class AbstractTokenInvitationUserServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var tokenInvitationUserService: TokenInvitationUserService

    @Autowired
    protected lateinit var integrationTestHelper: TokenInvitationUserIntegrationTestHelper

    @Autowired
    protected lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    protected lateinit var invitationUserIntegrationTestHelper: InvitationUserIntegrationTestHelper
    
    @Autowired
    protected lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper
    
    @Autowired
    protected lateinit var clientOrganizationIntegrationTestHelper: ClientOrganizationIntegrationTestHelper
}