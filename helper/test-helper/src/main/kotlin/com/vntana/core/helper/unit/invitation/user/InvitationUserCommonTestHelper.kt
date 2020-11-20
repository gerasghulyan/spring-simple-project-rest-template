package com.vntana.core.helper.unit.invitation.user

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser
import com.vntana.core.domain.invitation.user.InvitationOrganizationUser
import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.user.User
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.invitation.user.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:17 PM
 */
open class InvitationUserCommonTestHelper : AbstractCommonTestHelper() {
    private val userCommonTestHelper = UserCommonTestHelper()
    private val organizationCommonTestHelper = OrganizationCommonTestHelper()
    private val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    fun buildCreateInvitationUserForOrganizationDto(
            userRole: UserRole? = UserRole.ORGANIZATION_ADMIN,
            email: String? = uuid(),
            inviterUserUuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ) = CreateInvitationForOrganizationUserDto(userRole, email, inviterUserUuid, organizationUuid)

    fun buildCreateInvitationUserForClientsDto(
            clientUuid: String? = uuid(),
            email: String? = uuid(),
            inviterUserUuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ) = CreateInvitationForClientsUserDto(mapOf(clientUuid to UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER), email, inviterUserUuid, organizationUuid)

    fun buildInvitationUserPage(entities: List<InvitationOrganizationUser> = listOf(buildInvitationUserToOrganization(), buildInvitationUserToOrganization()),
                                pageAble: Pageable = buildPageRequest(0, 5),
                                total: Long = entities.size.toLong()
    ): Page<InvitationOrganizationUser> {
        return PageImpl(entities, pageAble, total)
    }

    fun buildInvitationUserToOrganization(
            userRole: UserRole? = UserRole.ORGANIZATION_ADMIN,
            email: String? = uuid(),
            status: InvitationStatus? = InvitationStatus.INVITED,
            invitedByUser: User? = userCommonTestHelper.buildUserWithOrganizationOwnerRole(),
            organization: Organization? = organizationCommonTestHelper.buildOrganization()
    ) = InvitationOrganizationUser(userRole, email, status, invitedByUser, organization)
    
    fun buildInvitationOrganizationClientUser(
            userRole: UserRole? = UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER,
            email: String? = uuid(),
            status: InvitationStatus? = InvitationStatus.INVITED,
            inviterUser: User? = userCommonTestHelper.buildUserWithOrganizationOwnerRole(),
            clientOrganization: ClientOrganization? = clientOrganizationCommonTestHelper.buildClientOrganization()
    ) = InvitationOrganizationClientUser(userRole, email, status, inviterUser, clientOrganization)

    fun buildUpdateInvitationUserStatusDto(
            uuid: String? = uuid(),
            status: InvitationStatus? = InvitationStatus.NOT_APPLICABLE
    ) = UpdateInvitationUserStatusDto(uuid, status)

    fun buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(
            email: String? = uuid(),
            organizationUuid: String? = uuid(),
            status: InvitationStatus? = InvitationStatus.INVITED
    ): GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto = GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(
            email,
            organizationUuid,
            status
    )

    fun buildGetAllByOrganizationUuidAndStatusInvitationUsersDto(
            page: Int = 0,
            size: Int = 5,
            organizationUuid: String? = organizationCommonTestHelper.buildOrganization().uuid,
            status: InvitationStatus? = InvitationStatus.INVITED
    ): GetAllByOrganizationUuidAndStatusInvitationUsersDto = GetAllByOrganizationUuidAndStatusInvitationUsersDto(page, size, organizationUuid, status)

    fun buildGetAllByStatusInvitationUsersPage(totalCount: Long = 0,
                                               tagGroups: List<InvitationOrganizationUser> = listOf(buildInvitationUserToOrganization())
    ): Page<InvitationOrganizationUser> = PageImpl(tagGroups, Pageable.unpaged(), totalCount)
}