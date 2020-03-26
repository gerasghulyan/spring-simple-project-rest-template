package com.vntana.core.service.invitation.organization

import com.vntana.core.helper.integration.invitation.organization.InvitationOrganizationIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 4:04 PM
 */
abstract class AbstractInvitationOrganizationServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var invitationOrganizationService: InvitationOrganizationService

    @Autowired
    protected lateinit var integrationTestHelper: InvitationOrganizationIntegrationTestHelper
}