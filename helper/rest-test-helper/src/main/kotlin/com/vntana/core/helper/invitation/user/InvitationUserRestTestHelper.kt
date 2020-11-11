package com.vntana.core.helper.invitation.user

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.user.request.*

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 5:55 PM
 */
open class InvitationUserRestTestHelper : AbstractRestTestHelper() {
    private fun email() = "${uuid()}@mail.com"

    fun buildCreateInvitationUserForOrganizationRequest(
            userRole: UserRoleModel? = UserRoleModel.ORGANIZATION_ADMIN,
            email: String? = email(),
            inviterUserUuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ): CreateInvitationForOrganizationUserRequest = CreateInvitationForOrganizationUserRequest(userRole, email, inviterUserUuid, organizationUuid)

    fun buildCreateInvitationUserForClientRequest(
            userRoles: Map<String, UserRoleModel>? = mapOf(Pair(uuid(), UserRoleModel.CLIENT_CONTENT_MANAGER)),
            email: String? = email(),
            inviterUserUuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ): CreateInvitationForOrganizationClientUserRequest = CreateInvitationForOrganizationClientUserRequest(userRoles, email, inviterUserUuid, organizationUuid)

    fun buildGetAllByStatusInvitationUserRequest(
            page: Int = 0,
            size: Int = 5,
            organizationUuid: String? = uuid(),
            invitationStatus: InvitationStatusModel? = InvitationStatusModel.INVITED
    ): GetAllByStatusInvitationUserRequest = GetAllByStatusInvitationUserRequest(page, size, organizationUuid, invitationStatus)

    fun buildUpdateInvitationUserInvitationStatusRequest(
            uuid: String? = uuid(),
            status: InvitationStatusModel? = InvitationStatusModel.ACCEPTED
    ): UpdateInvitationUserInvitationStatusRequest = UpdateInvitationUserInvitationStatusRequest(uuid, status)

    fun buildAcceptInvitationUserRequest(
            token: String? = uuid()
    ): AcceptInvitationUserRequest = AcceptInvitationUserRequest(token)

    fun buildAcceptInvitationUserAndSignUpRequest(
            token: String? = uuid(),
            newUserFullName: String? = uuid(),
            password: String? = uuid()
    ): AcceptInvitationUserAndSignUpRequest = AcceptInvitationUserAndSignUpRequest(token, newUserFullName, password)

    fun buildSendInvitationUserRequest(
            email: String? = email(),
            token: String? = uuid(),
            inviterUserUuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ): SendInvitationUserRequest = SendInvitationUserRequest(email, token, inviterUserUuid, organizationUuid)
}