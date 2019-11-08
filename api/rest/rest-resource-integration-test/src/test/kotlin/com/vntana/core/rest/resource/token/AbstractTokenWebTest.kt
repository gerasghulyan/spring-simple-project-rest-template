package com.vntana.core.rest.resource.token

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient
import com.vntana.core.helper.token.TokenResorceTestHelper
import com.vntana.core.rest.client.token.ResetPasswordTokenResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:51 PM
 */
abstract class AbstractTokenWebTest : AbstractWebIntegrationTest() {
    protected val restTestHelper = TokenResorceTestHelper()

    @Autowired
    protected lateinit var resetPasswordTokenResourceClient: ResetPasswordTokenResourceClient

    @Autowired
    protected lateinit var emailNotificationResourceClient: EmailNotificationResourceClient
}