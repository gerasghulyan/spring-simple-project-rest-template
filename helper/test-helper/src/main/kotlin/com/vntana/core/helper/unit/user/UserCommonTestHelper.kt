package com.vntana.core.helper.unit.user

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.user.User
import com.vntana.core.domain.user.UserClientOrganizationAdminRole
import com.vntana.core.domain.user.UserOrganizationAdminRole
import com.vntana.core.domain.user.UserSuperAdminRole
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.service.token.reset.password.dto.CreateTokenResetPasswordDto
import com.vntana.core.service.user.dto.CreateUserDto
import com.vntana.core.service.user.dto.CreateUserWithOwnerRoleDto
import com.vntana.core.service.user.dto.UpdateUserDto
import java.time.LocalDateTime

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 3:46 PM
 */
open class UserCommonTestHelper : AbstractCommonTestHelper() {

    private val organizationCommonTestHelper = OrganizationCommonTestHelper()

    private val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    fun buildCreateUserWithOwnerRoleDto(
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid(),
            organizationUuid: String? = uuid()
    ): CreateUserWithOwnerRoleDto = CreateUserWithOwnerRoleDto(fullName, email, password, organizationUuid)

    fun buildCreateUserDto(
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid()
    ): CreateUserDto = CreateUserDto(fullName, email, password)

    fun buildUserWithWithSuperAdminRole(
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid()
    ): User {
        val user = User(fullName, email, password)
        val userSuperAdminRole = UserSuperAdminRole(user)
        user.setRoles(listOf(userSuperAdminRole))
        return user
    }

    fun buildUserWithOrganizationOwnerRole(
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid(),
            organization: Organization? = organizationCommonTestHelper.buildOrganization()
    ): User {
        val user = User(fullName, email, password)
        user.grantOrganizationOwnerRole(organization)
        return user
    }

    fun buildUserWithOrganizationAdminRole(
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid(),
            organization: Organization? = organizationCommonTestHelper.buildOrganization()
    ): User {
        val user = User(fullName, email, password)
        val adminRole = UserOrganizationAdminRole(user, organization)
        user.setRoles(listOf(adminRole))
        return user
    }

    fun buildUserWithClientAdminRole(
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid(),
            clientOrganization: ClientOrganization? = clientOrganizationCommonTestHelper.buildClientOrganization()
    ): User {
        val user = User(fullName, email, password)
        val clientAdminRole = UserClientOrganizationAdminRole(user, clientOrganization)
        user.setRoles(listOf(clientAdminRole))
        return user
    }

    fun buildUser(
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid()
    ): User = User(fullName, email, password)

    fun buildUserInvalidEmail(): String = uuid()

    fun buildUpdateUserDto(
            uuid: String? = uuid(),
            imageBlobId: String? = uuid(),
            fullName: String? = uuid()
    ): UpdateUserDto {
        return UpdateUserDto(uuid, imageBlobId, fullName)
    }

    fun buildCreateTokenResetPasswordDto(
            token: String? = uuid(),
            userUuid: String? = uuid(),
            expirationDate: LocalDateTime? = LocalDateTime.now().plusHours(1)
    ): CreateTokenResetPasswordDto {
        return CreateTokenResetPasswordDto(token, userUuid, expirationDate)
    }
}