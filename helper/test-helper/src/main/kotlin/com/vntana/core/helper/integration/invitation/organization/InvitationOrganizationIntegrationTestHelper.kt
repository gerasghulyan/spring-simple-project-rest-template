package com.vntana.core.helper.integration.invitation.organization

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
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

    fun persistInvitationOrganization(ownerFullName: String? = uuid(),
                                      email: String? = uuid(),
                                      organizationName: String? = uuid(),
                                      slug: String? = uuid(),
                                      customerSubscriptionDefinitionUuid: String? = uuid(),
                                      status: InvitationStatus? = InvitationStatus.INVITED): InvitationOrganization {
        val dto = buildCreateInvitationOrganizationDto(ownerFullName = ownerFullName,
                email = email,
                organizationName = organizationName,
                slug = slug,
                customerSubscriptionDefinitionUuid = customerSubscriptionDefinitionUuid,
                status = status
        )
        return invitationOrganizationService.create(dto)
    }
}