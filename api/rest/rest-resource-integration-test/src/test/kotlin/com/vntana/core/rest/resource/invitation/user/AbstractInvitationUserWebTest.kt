package com.vntana.core.rest.resource.invitation.user

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient
import com.vntana.core.helper.client.ClientOrganizationResourceTestHelper
import com.vntana.core.helper.invitation.user.InvitationUserResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.token.TokenResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.helper.user.role.UserRoleResourceTestHelper
import com.vntana.core.rest.client.invitation.user.InvitationUserResourceClient
import com.vntana.core.rest.client.token.TokenResourceClient
import com.vntana.core.rest.client.user.UserResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:41 PM
 */
abstract class AbstractInvitationUserWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var invitationUserResourceClient: InvitationUserResourceClient

    @Autowired
    protected lateinit var userResourceClient: UserResourceClient

    @Autowired
    protected lateinit var tokenResourceClient: TokenResourceClient

    @Autowired
    protected lateinit var resourceTestHelper: InvitationUserResourceTestHelper

    @Autowired
    protected lateinit var tokenResourceTestHelper: TokenResourceTestHelper

    @Autowired
    protected lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    @Autowired
    protected lateinit var clientOrganizationResourceTestHelper: ClientOrganizationResourceTestHelper
    
    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper

    @Autowired
    protected lateinit var invitationUserResourceTestHelper: InvitationUserResourceTestHelper

    @Autowired
    protected lateinit var emailNotificationResourceClient: EmailNotificationResourceClient

    @Autowired
    protected lateinit var userRoleResourceTestHelper: UserRoleResourceTestHelper
}