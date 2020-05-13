package com.vntana.core.helper.invitation.user

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.request.CreateInvitationUserRequest

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 5:55 PM
 */
open class InvitationUserRestTestHelper : AbstractRestTestHelper() {

    fun buildCreateInvitationUserRequest(
            userRole: UserRoleModel? = UserRoleModel.ORGANIZATION_ADMIN,
            email: String? = "${uuid()}@mail.com",
            inviterUserUuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ): CreateInvitationUserRequest = CreateInvitationUserRequest(userRole, email, inviterUserUuid, organizationUuid)
}