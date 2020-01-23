package com.vntana.core.helper.unit.user

import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.user.User
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.service.user.dto.CreateUserDto
import com.vntana.core.service.user.dto.UpdateUserDto
import com.vntana.core.service.user.dto.UserGrantOrganizationRoleDto

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 3:46 PM
 */
open class UserCommonTestHelper : AbstractCommonTestHelper() {

    private val organizationCommonTestHelper = OrganizationCommonTestHelper()

    fun buildUserCreateDto(
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid(),
            organizationUuid: String? = uuid(),
            role: UserRole? = UserRole.CLIENT_ADMIN
    ): CreateUserDto = CreateUserDto(fullName, email, password, organizationUuid, role)

    fun buildUser(
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid(),
            clientOrganization: Organization? = organizationCommonTestHelper.buildOrganization()
    ): User {
        val user = User(fullName, email, password)
        user.grantOrganizationRole(clientOrganization)
        return user
    }

    fun buildUserInvalidEmail(): String = uuid()

    fun buildUserGrantOrganizationRoleDto(
            uuid: String? = uuid(),
            organizationUuid: String? = uuid(),
            role: UserRole? = UserRole.CLIENT_ADMIN
    ): UserGrantOrganizationRoleDto = UserGrantOrganizationRoleDto(uuid, organizationUuid, role)

    fun buildUpdateUserDto(
            uuid: String? = uuid(),
            imageBlobId: String? = uuid(),
            fullName: String? = uuid()
    ): UpdateUserDto {
        return UpdateUserDto(uuid, imageBlobId, fullName)
    }
}