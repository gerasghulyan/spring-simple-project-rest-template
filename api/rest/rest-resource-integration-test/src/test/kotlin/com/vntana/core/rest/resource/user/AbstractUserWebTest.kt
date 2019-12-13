package com.vntana.core.rest.resource.user

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient
import com.sflpro.notifier.api.model.common.result.ResultResponseModel
import com.sflpro.notifier.api.model.email.response.CreateEmailNotificationResponse
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.rest.client.user.UserResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.reset
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:24 AM
 */
abstract class AbstractUserWebTest : AbstractWebIntegrationTest() {
    @Autowired
    protected lateinit var resourceHelper: UserResourceTestHelper

    @Autowired
    lateinit var userResourceClient: UserResourceClient

    @Autowired
    protected lateinit var emailNotificationResourceClient: EmailNotificationResourceClient

    fun email(): String = uuid() + "@gmail.com"

    @Before
    fun prepare() {
        reset(emailNotificationResourceClient)
        `when`(emailNotificationResourceClient.createEmailNotification(ArgumentMatchers.any())).thenReturn(ResultResponseModel<CreateEmailNotificationResponse>())
    }
}