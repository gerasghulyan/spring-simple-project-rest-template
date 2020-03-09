package com.vntana.core.helper.unit.organization

import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.service.organization.dto.CreateOrganizationDto
import com.vntana.core.service.organization.dto.GetUserOrganizationsByUserUuidAndRoleDto
import com.vntana.core.service.organization.dto.UpdateOrganizationDto

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
            imageBlobId: String? = uuid()
    ): Organization = Organization(name, slug, imageBlobId)

    fun buildGetUserOrganizationsByUserUuidAndRoleDto(
            userUuid: String? = uuid(),
            userRole: UserRole? = UserRole.ORGANIZATION_ADMIN
    ) = GetUserOrganizationsByUserUuidAndRoleDto(userUuid, userRole)

}