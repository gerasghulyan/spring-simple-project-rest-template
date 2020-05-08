package com.vntana.core.helper.integration.user

import com.vntana.core.domain.user.User
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.dto.CreateUserDto
import com.vntana.core.service.user.dto.UserGrantOrganizationRoleDto
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

    fun persistUser(organizationUuid: String = organizationIntegrationTestHelper.persistOrganization().uuid,
                    fullName: String? = uuid(),
                    email: String? = uuid(),
                    password: String? = uuid()): User {
        val dto: CreateUserDto = buildUserCreateDto(organizationUuid = organizationUuid, fullName = fullName, email = email, password = password)
        return userService.create(dto)
    }

    fun grantOrganizationRole(
            uuid: String? = uuid(),
            organizationUuid: String? = uuid(),
            role: UserRole? = UserRole.ORGANIZATION_OWNER
    ) {
        userService.grantOrganizationRole(UserGrantOrganizationRoleDto(uuid, organizationUuid, role))
    }

    fun persistVerifiedUser(organizationUuid: String = organizationIntegrationTestHelper.persistOrganization().uuid,
                            fullName: String? = uuid(),
                            email: String? = uuid()): User {
        val user = persistUser(organizationUuid, fullName, email)
        return userService.makeVerified(user.email)
    }
}