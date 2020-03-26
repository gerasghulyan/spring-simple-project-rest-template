package com.vntana.core.rest.resource.invitation.organization

import com.vntana.core.helper.invitation.organization.InvitationOrganizationResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.rest.client.invitation.organization.InvitationOrganizationResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:41 PM
 */
abstract class AbstractInvitationOrganizationWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var invitationOrganizationResourceClient: InvitationOrganizationResourceClient

    @Autowired
    protected lateinit var resourceTestHelper: InvitationOrganizationResourceTestHelper

    @Autowired
    protected lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

}