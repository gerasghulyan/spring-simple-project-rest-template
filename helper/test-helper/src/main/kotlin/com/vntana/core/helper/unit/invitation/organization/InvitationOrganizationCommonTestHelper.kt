package com.vntana.core.helper.unit.invitation.organization

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.service.invitation.organization.dto.CreateInvitationOrganizationDto

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 3:35 PM
 */
open class InvitationOrganizationCommonTestHelper : AbstractCommonTestHelper() {

    fun buildCreateInvitationOrganizationDto(
            ownerFullName: String? = uuid(),
            email: String? = uuid(),
            organizationName: String? = uuid(),
            slug: String? = uuid(),
            customerSubscriptionDefinitionUuid: String? = uuid(),
            status: InvitationStatus? = InvitationStatus.INVITED
    ) = CreateInvitationOrganizationDto(ownerFullName, email, organizationName, slug, customerSubscriptionDefinitionUuid, status)

    fun buildInvitationOrganization(
            ownerFullName: String? = uuid(),
            email: String? = uuid(),
            organizationName: String? = uuid(),
            slug: String? = uuid(),
            customerSubscriptionDefinitionUuid: String? = uuid(),
            status: InvitationStatus? = InvitationStatus.INVITED
    ) = InvitationOrganization(ownerFullName, email, organizationName, slug, customerSubscriptionDefinitionUuid, status)
}