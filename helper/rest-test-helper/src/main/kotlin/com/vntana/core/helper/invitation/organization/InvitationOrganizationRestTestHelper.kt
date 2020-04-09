package com.vntana.core.helper.invitation.organization

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.organization.request.CreateInvitationOrganizationRequest
import com.vntana.core.model.invitation.organization.request.RejectInvitationOrganizationRequest
import com.vntana.core.model.invitation.organization.request.SendInvitationOrganizationRequest
import com.vntana.core.model.invitation.organization.request.UpdateInvitationOrganizationStatusRequest

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:45 PM
 */
open class InvitationOrganizationRestTestHelper : AbstractRestTestHelper() {

    fun buildCreateInvitationOrganizationRequest(ownerFullName: String? = uuid(),
                                                 email: String? = uuid(),
                                                 organizationName: String? = uuid(),
                                                 slug: String? = uuid(),
                                                 customerSubscriptionDefinitionUuid: String? = uuid()
    ): CreateInvitationOrganizationRequest = CreateInvitationOrganizationRequest(
            ownerFullName, email, organizationName, slug, customerSubscriptionDefinitionUuid
    )

    fun buildSendInvitationOrganizationRequest(
            email: String? = uuid(),
            token: String? = uuid(),
            organizationName: String? = uuid()
    ): SendInvitationOrganizationRequest = SendInvitationOrganizationRequest(email, token, organizationName)

    fun buildUpdateInvitationOrganizationStatusRequest(
            uuid: String? = uuid(),
            status: InvitationStatusModel? = InvitationStatusModel.ACCEPTED
    ) = UpdateInvitationOrganizationStatusRequest(uuid, status)

    fun buildRejectInvitationOrganizationRequest(
            uuid: String? = uuid(),
            token: String? = uuid()
    ) = RejectInvitationOrganizationRequest(uuid, token)
}