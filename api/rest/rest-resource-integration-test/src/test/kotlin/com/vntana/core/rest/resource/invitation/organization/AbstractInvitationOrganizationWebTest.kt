package com.vntana.core.rest.resource.invitation.organization

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient
import com.vntana.core.helper.invitation.organization.InvitationOrganizationResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.token.TokenResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.indexation.producer.invitation.organization.InvitationOrganizationUuidAwareActionProducer
import com.vntana.core.rest.client.invitation.organization.InvitationOrganizationResourceClient
import com.vntana.core.rest.client.user.UserResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.reset
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
    protected lateinit var tokenResourceTestHelper: TokenResourceTestHelper

    @Autowired
    protected lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    @Autowired
    protected lateinit var invitationOrganizationUuidAwareActionProducer: InvitationOrganizationUuidAwareActionProducer

    @Autowired
    protected lateinit var emailNotificationResourceClient: EmailNotificationResourceClient

    @Autowired
    protected lateinit var userResourceClient: UserResourceClient

    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper
    
    @Before
    fun before() {
        reset(invitationOrganizationUuidAwareActionProducer)
        doNothing().`when`(invitationOrganizationUuidAwareActionProducer).produce(ArgumentMatchers.any())
    }
}