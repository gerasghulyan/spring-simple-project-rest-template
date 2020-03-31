package com.vntana.core.helper.invitation.organization

import com.vntana.core.rest.client.invitation.organization.InvitationOrganizationResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:46 PM
 */
@Component
class InvitationOrganizationResourceTestHelper : InvitationOrganizationRestTestHelper() {

    @Autowired
    private lateinit var invitationOrganizationResourceClient: InvitationOrganizationResourceClient

    fun persistInvitationOrganization(ownerFullName: String? = uuid(),
                                      email: String? = uuid(),
                                      organizationName: String? = uuid(),
                                      slug: String? = uuid(),
                                      customerSubscriptionDefinitionUuid: String? = uuid()
    ): String {
        val request = buildCreateInvitationOrganizationRequest(
                ownerFullName = ownerFullName,
                email = email,
                organizationName = organizationName,
                slug = slug,
                customerSubscriptionDefinitionUuid = customerSubscriptionDefinitionUuid
        )
        return invitationOrganizationResourceClient.create(request)?.body?.response()?.uuid.toString()
    }

}