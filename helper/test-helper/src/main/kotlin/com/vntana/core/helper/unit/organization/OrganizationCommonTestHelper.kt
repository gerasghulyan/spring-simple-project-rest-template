package com.vntana.core.helper.unit.organization

import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.organization.status.OrganizationStatus
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.service.organization.dto.CreateOrganizationDto
import com.vntana.core.service.organization.dto.GetAllOrganizationDto
import com.vntana.core.service.organization.dto.GetUserOrganizationsByUserUuidAndRoleDto
import com.vntana.core.service.organization.dto.UpdateOrganizationDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 6:05 PM
 */
open class OrganizationCommonTestHelper : AbstractCommonTestHelper() {

    fun buildCreateOrganizationDto(
            name: String? = uuid(),
            slug: String? = uuid(),
            imageBlobId: String? = uuid()
    ): CreateOrganizationDto = CreateOrganizationDto(name, slug, imageBlobId)

    fun buildUpdateOrganizationDto(
            uuid: String? = uuid(),
            imageBlobId: String? = uuid(),
            name: String? = uuid()
    ): UpdateOrganizationDto = UpdateOrganizationDto(uuid, imageBlobId, name)


    fun buildOrganization(
            name: String? = uuid(),
            slug: String? = uuid(),
            imageBlobId: String? = uuid(),
            status: OrganizationStatus? = OrganizationStatus.ACTIVE
    ): Organization = Organization(name, slug, imageBlobId, status)

    fun buildGetUserOrganizationsByUserUuidAndRoleDto(
            userUuid: String? = uuid(),
            userRole: UserRole? = UserRole.ORGANIZATION_ADMIN
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