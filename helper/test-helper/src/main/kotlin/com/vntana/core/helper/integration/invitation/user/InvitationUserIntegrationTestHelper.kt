package com.vntana.core.helper.integration.invitation.user

import com.vntana.core.domain.invitation.user.InvitationUser
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.service.invitation.user.InvitationUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:17 PM
 */
@Component
class InvitationUserIntegrationTestHelper : InvitationUserCommonTestHelper() {

    @Autowired
    private lateinit var invitationUserService: InvitationUserService

    @Autowired
    private lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    fun persistInvitationUser(
            userRole: UserRole? = UserRole.ORGANIZATION_ADMIN,
            email: String? = uuid(),
            inviterUserUuid: String? = userIntegrationTestHelper.persistUser().uuid
    ): InvitationUser = invitationUserService.create(buildCreateInvitationUserDto(userRole, email, inviterUserUuid));
}