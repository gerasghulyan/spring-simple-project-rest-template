package com.vntana.core.helper.invitation.user

import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.user.response.UpdateInvitationUserInvitationStatusResultResponse
import com.vntana.core.rest.client.invitation.user.InvitationUserResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 6:33 PM
 */
@Component
class InvitationUserResourceTestHelper : InvitationUserRestTestHelper() {

    @Autowired
    private lateinit var invitationUserResourceClient: InvitationUserResourceClient

    @Autowired
    private lateinit var userResourceTestHelper: UserResourceTestHelper

    @Autowired
    private lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    fun persistInvitationUser(
            userRole: UserRoleModel? = UserRoleModel.ORGANIZATION_ADMIN,
            email: String? = uuid(),
            inviterUserUuid: String? = userResourceTestHelper.persistUser().response().uuid,
            organizationUuid: String? = organizationResourceTestHelper.persistOrganization().response().uuid
    ): String = invitationUserResourceClient.create(buildCreateInvitationUserRequest(userRole, email, inviterUserUuid, organizationUuid))?.body?.response()?.uuid.toString()

    fun updateInvitationStatus(
            uuid: String? = uuid(),
            status: InvitationStatusModel? = InvitationStatusModel.ACCEPTED
    ): ResponseEntity<UpdateInvitationUserInvitationStatusResultResponse> = invitationUserResourceClient.updateStatus(buildUpdateInvitationUserInvitationStatusRequest(uuid, status))
}