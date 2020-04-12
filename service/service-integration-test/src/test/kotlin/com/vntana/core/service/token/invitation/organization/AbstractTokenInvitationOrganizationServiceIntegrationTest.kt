package com.vntana.core.service.token.invitation.organization

import com.vntana.core.helper.integration.invitation.organization.InvitationOrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.token.auth.AuthTokenIntegrationTestHelper
import com.vntana.core.helper.integration.token.invitation.organization.TokenInvitationOrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Geras Ghulyan
 * Date: 11.04.20
 * Time: 23:37
 */
abstract class AbstractTokenInvitationOrganizationServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var tokenInvitationOrganizationService: TokenInvitationOrganizationService

    @Autowired
    protected lateinit var integrationTestHelper: TokenInvitationOrganizationIntegrationTestHelper

    @Autowired
    protected lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    protected lateinit var invitationOrganizationIntegrationTestHelper: InvitationOrganizationIntegrationTestHelper
}