package com.vntana.core.helper.integration.invitation.user

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser
import com.vntana.core.domain.invitation.user.InvitationOrganizationUser
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.service.invitation.user.InvitationUserToClientService
import com.vntana.core.service.invitation.user.InvitationUserToOrganizationService
import com.vntana.core.service.invitation.user.dto.UpdateInvitationUserStatusDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:17 PM
 */
@Component
class InvitationUserIntegrationTestHelper : InvitationUserCommonTestHelper() {

    @Autowired
    private lateinit var invitationUserToOrganizationService: InvitationUserToOrganizationService
    
    @Autowired
    private lateinit var invitationUserToClientService:InvitationUserToClientService

    @Autowired
    private lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    private lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper

    fun persistInvitationUserToOrganization(
            userRole: UserRole? = UserRole.ORGANIZATION_ADMIN,
            email: String? = uuid(),
            inviterUserUuid: String? = userIntegrationTestHelper.persistUserWithOwnerRole().uuid,
            organizationUuid: String? = organizationIntegrationTestHelper.persistOrganization().uuid
    ): InvitationOrganizationUser = invitationUserToOrganizationService.create(buildCreateInvitationUserForOrganizationDto(userRole, email, inviterUserUuid, organizationUuid))

    fun updateInvitationUserToOrganizationStatus(
            uuid: String? = uuid(),
            status: InvitationStatus? = InvitationStatus.NOT_APPLICABLE,
            dto: UpdateInvitationUserStatusDto? = buildUpdateInvitationUserStatusDto(uuid, status)
    ): InvitationOrganizationUser = invitationUserToOrganizationService.updateStatus(dto)
    
    fun persistInvitationUserToClient(
            clientUuid: String? = uuid(),
            email: String? = uuid(),
            inviterUserUuid: String? = userIntegrationTestHelper.persistUserWithOwnerRole().uuid,
            organizationUuid: String? = organizationIntegrationTestHelper.persistOrganization().uuid
    ): InvitationOrganizationClientUser = invitationUserToClientService.create(buildCreateInvitationUserForClientsDto(clientUuid, email, inviterUserUuid, organizationUuid)).get(0)
}