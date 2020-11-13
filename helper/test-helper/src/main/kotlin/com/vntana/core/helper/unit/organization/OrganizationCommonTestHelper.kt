package com.vntana.core.helper.unit.organization

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.organization.status.OrganizationStatus
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.service.organization.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 6:05 PM
 */
open class OrganizationCommonTestHelper : AbstractCommonTestHelper() {

    protected val invitationOrganizationCommonTestHelper = InvitationOrganizationCommonTestHelper()

    fun buildCreateOrganizationDto(
            name: String? = uuid(),
            slug: String? = uuid(),
            imageBlobId: String? = uuid()
    ): CreateOrganizationDto = CreateOrganizationDto(name, slug, imageBlobId)

    fun buildCreateOrganizationFromInvitationDto(
            name: String? = uuid(),
            slug: String? = uuid(),
            organizationInvitationUuid: String? = uuid()
    ): CreateOrganizationFromInvitationDto = CreateOrganizationFromInvitationDto(name, slug, organizationInvitationUuid)

    fun buildUpdateOrganizationDto(
            uuid: String? = uuid(),
            imageBlobId: String? = uuid(),
            name: String? = uuid(),
            status: OrganizationStatus? = OrganizationStatus.DISABLED
    ): UpdateOrganizationDto = UpdateOrganizationDto(uuid, imageBlobId, name, status)

    fun buildOrganization(
            name: String? = uuid(),
            slug: String? = uuid(),
            imageBlobId: String? = uuid(),
            status: OrganizationStatus? = OrganizationStatus.ACTIVE
    ): Organization = Organization(name, slug, imageBlobId, status)

    fun buildOrganizationWithClients(
            name: String? = uuid(),
            slug: String? = uuid(),
            imageBlobId: String? = uuid(),
            status: OrganizationStatus? = OrganizationStatus.ACTIVE,
            clientOrganization: List<ClientOrganization>? = emptyList()
    ): Organization = Organization(name, slug, imageBlobId, status, clientOrganization)

    fun buildOrganizationWithInvitation(
            name: String? = uuid(),
            slug: String? = uuid(),
            invitation: InvitationOrganization? = invitationOrganizationCommonTestHelper.buildInvitationOrganization()
    ): Organization = Organization(name, slug, invitation)

    fun buildGetUserOrganizationsByUserUuidAndRoleDto(
            userUuid: String? = uuid(),
            userRole: UserRole? = UserRole.ORGANIZATION_OWNER
    ) = GetUserOrganizationsByUserUuidAndRoleDto(userUuid, userRole)

    fun buildGetAllOrganizationDto(
            page: Int = 0,
            size: Int = 5
    ): GetAllOrganizationDto = GetAllOrganizationDto(page, size)

    fun buildOrganizationPage(organizations: List<Organization> = listOf(buildOrganization(), buildOrganization(), buildOrganization()),
                              pageAble: Pageable = buildPageRequest(0, 5),
                              total: Long = organizations.size.toLong()
    ): Page<Organization> {
        return PageImpl(organizations, pageAble, total)
    }
}