package com.vntana.core.rest.resource.invitation

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient
import com.sflpro.notifier.api.model.common.result.ResultResponseModel
import com.sflpro.notifier.api.model.email.response.CreateEmailNotificationResponse
import com.vntana.core.helper.invitation.InvitationResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.rest.client.invitation.InvitationResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 10:47 AM
 */
abstract class AbstractInvitationWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var invitationResourceClient: InvitationResourceClient

    @Autowired
    protected lateinit var resourceTestHelper: InvitationResourceTestHelper

    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper

    @Autowired
    protected lateinit var emailNotificationResourceClient: EmailNotificationResourceClient

    @Before
    fun prepare() {
        Mockito.reset(emailNotificationResourceClient)
        Mockito.`when`(emailNotificationResourceClient.createEmailNotification(ArgumentMatchers.any()))
                .thenReturn(ResultResponseModel<CreateEmailNotificationResponse>())
    }
}