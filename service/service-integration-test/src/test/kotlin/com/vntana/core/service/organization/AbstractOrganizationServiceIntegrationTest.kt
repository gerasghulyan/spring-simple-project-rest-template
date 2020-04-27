package com.vntana.core.service.organization

import com.vntana.core.helper.integration.invitation.organization.InvitationOrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 10:46 AM
 */
abstract class AbstractOrganizationServiceIntegrationTest : AbstractServiceIntegrationTest() {
    @Autowired
    protected lateinit var organizationService: OrganizationService

    @Autowired
    protected lateinit var integrationTestHelper: OrganizationIntegrationTestHelper

    @Autowired
    protected lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    protected lateinit var invitationOrganizationIntegrationTestHelper: InvitationOrganizationIntegrationTestHelper

    protected val commonTestHelper: OrganizationCommonTestHelper = OrganizationCommonTestHelper()
}