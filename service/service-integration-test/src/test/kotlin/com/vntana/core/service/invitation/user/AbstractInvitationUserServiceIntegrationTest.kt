package com.vntana.core.service.invitation.user

import com.vntana.core.helper.integration.client.ClientOrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.invitation.user.InvitationUserIntegrationTestHelper
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.token.invitation.user.TokenInvitationUserIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:15 PM
 */
abstract class AbstractInvitationUserServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var invitationUserService: InvitationUserService

    @Autowired
    protected lateinit var integrationInvitationUserTestHelper: InvitationUserIntegrationTestHelper
    
    @Autowired
    protected lateinit var userIntegrationTestHelper: UserIntegrationTestHelper
    
    @Autowired
    protected lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper
    
    @Autowired
    protected lateinit var clientOrganizationIntegrationTestHelper: ClientOrganizationIntegrationTestHelper
    
    @Autowired
    protected lateinit var tokenInvitationUserIntegrationTestHelper : TokenInvitationUserIntegrationTestHelper
}