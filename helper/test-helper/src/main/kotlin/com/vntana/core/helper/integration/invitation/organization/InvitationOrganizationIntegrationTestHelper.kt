package com.vntana.core.helper.integration.invitation.organization

import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import com.vntana.core.service.token.dto.CreateTokenInvitationOrganizationDto
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 3:44 PM
 */
@Component
class InvitationOrganizationIntegrationTestHelper : InvitationOrganizationCommonTestHelper() {

    @Autowired
    private lateinit var invitationOrganizationService: InvitationOrganizationService

    @Autowired
    private lateinit var tokenInvitationOrganizationService: TokenInvitationOrganizationService

    fun persistInvitationOrganization(
            ownerFullName: String? = uuid(),
            email: String? = uuid(),
            organizationName: String? = uuid(),
            slug: String? = uuid(),
            customerSubscriptionDefinitionUuid: String? = uuid(),
            token: String? = uuid()): InvitationOrganization {
        val dto = buildCreateInvitationOrganizationDto(ownerFullName = ownerFullName,
                email = email,
                organizationName = organizationName,
                slug = slug,
                customerSubscriptionDefinitionUuid = customerSubscriptionDefinitionUuid
        )
        val invitationOrganization = invitationOrganizationService.create(dto)
        tokenInvitationOrganizationService.create(CreateTokenInvitationOrganizationDto(token, invitationOrganization.uuid))
        return invitationOrganization;
    }
}