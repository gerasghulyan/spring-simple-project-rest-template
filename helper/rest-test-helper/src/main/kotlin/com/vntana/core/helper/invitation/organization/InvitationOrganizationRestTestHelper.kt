package com.vntana.core.helper.invitation.organization

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.invitation.organization.request.CreateInvitationOrganizationRequest

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
}