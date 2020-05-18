package com.vntana.core.helper.integration.user

import com.vntana.core.domain.user.User
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.dto.CreateUserWithOwnerRoleDto
import com.vntana.core.service.user.role.UserRoleService
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 2:35 PM
 */
@Component
class UserIntegrationTestHelper : UserCommonTestHelper() {

    @Autowired
    private lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRoleService: UserRoleService

    fun persistUserWithOwnerRole(organizationUuid: String = organizationIntegrationTestHelper.persistOrganization().uuid,
                                 fullName: String? = uuid(),
                                 email: String? = uuid(),
                                 password: String? = uuid()): User {
        val withOwnerRoleDto: CreateUserWithOwnerRoleDto = buildCreateUserWithOwnerRoleDto(organizationUuid = organizationUuid, fullName = fullName, email = email, password = password)
        return userService.createWithOwnerRole(withOwnerRoleDto)
    }

    fun persistUser(fullName: String? = uuid(),
                    email: String? = uuid(),
                    password: String? = uuid()): User {
        val dto = buildCreateUserDto(fullName = fullName, email = email, password = password)
        return userService.create(dto)
    }

    fun grantOrganizationOwnerRole(
            uuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ) {
        userRoleService.grantOrganizationOwnerRole(UserGrantOrganizationRoleDto(uuid, organizationUuid))
    }

    fun persistVerifiedUser(organizationUuid: String = organizationIntegrationTestHelper.persistOrganization().uuid,
                            fullName: String? = uuid(),
                            email: String? = uuid()): User {
        val user = persistUserWithOwnerRole(organizationUuid, fullName, email)
        return userService.makeVerified(user.email)
    }
}