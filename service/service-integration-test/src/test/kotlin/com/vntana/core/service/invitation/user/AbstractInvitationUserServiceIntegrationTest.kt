package com.vntana.core.service.invitation.user

import com.vntana.core.helper.integration.invitation.user.InvitationUserIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
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
    protected lateinit var integrationUserTestHelper: InvitationUserIntegrationTestHelper
    
    @Autowired
    protected lateinit var userIntegrationTestHelper: UserIntegrationTestHelper
}