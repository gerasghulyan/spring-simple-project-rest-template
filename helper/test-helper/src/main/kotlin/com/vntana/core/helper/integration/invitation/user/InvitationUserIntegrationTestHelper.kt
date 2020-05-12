package com.vntana.core.helper.integration.invitation.user

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.domain.invitation.user.InvitationUser
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.service.invitation.user.InvitationUserService
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
    private lateinit var invitationUserService: InvitationUserService

    @Autowired
    private lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    private lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper

    fun persistInvitationUser(
            userRole: UserRole? = UserRole.ORGANIZATION_ADMIN,
            email: String? = uuid(),
            inviterUserUuid: String? = userIntegrationTestHelper.persistUser().uuid,
            organizationUuid: String? = organizationIntegrationTestHelper.persistOrganization().uuid
    ): InvitationUser = invitationUserService.create(buildCreateInvitationUserDto(userRole, email, inviterUserUuid, organizationUuid))

    fun updateInvitationUserStatus(
            uuid: String? = uuid(),
            status: InvitationStatus? = InvitationStatus.NOT_APPLICABLE,
            dto: UpdateInvitationUserStatusDto? = buildUpdateInvitationUserStatusDto(uuid, status)
    ) = invitationUserService.updateStatus(dto)
}