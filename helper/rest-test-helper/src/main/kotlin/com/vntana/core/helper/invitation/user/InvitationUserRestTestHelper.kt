package com.vntana.core.helper.invitation.user

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.user.request.CreateInvitationUserRequest
import com.vntana.core.model.invitation.user.request.GetAllByStatusInvitationUserRequest
import com.vntana.core.model.invitation.user.request.UpdateInvitationUserInvitationStatusRequest

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

    fun buildGetAllByStatusInvitationUserRequest(
            page: Int = 0,
            size: Int = 5,
            invitationStatus: InvitationStatusModel? = InvitationStatusModel.INVITED
    ): GetAllByStatusInvitationUserRequest = GetAllByStatusInvitationUserRequest(page, size, invitationStatus)

    fun buildUpdateInvitationUserInvitationStatusRequest(
            uuid: String? = uuid(),
            status: InvitationStatusModel? = InvitationStatusModel.ACCEPTED
    ): UpdateInvitationUserInvitationStatusRequest = UpdateInvitationUserInvitationStatusRequest(uuid, status)
}