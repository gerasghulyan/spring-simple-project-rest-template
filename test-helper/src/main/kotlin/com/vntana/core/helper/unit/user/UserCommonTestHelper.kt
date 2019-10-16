package com.vntana.core.helper.unit.user

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.domain.user.User
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.service.user.dto.UserCreateDto

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 3:46 PM
 */
open class UserCommonTestHelper : AbstractCommonTestHelper() {

    private val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    fun buildUserCreateDto(
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid(),
            clientOrganizationUuid: String? = uuid(),
            role: UserRole? = UserRole.CLIENT_ADMIN
    ): UserCreateDto = UserCreateDto(fullName, email, password, clientOrganizationUuid, role)

    fun buildUser(
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid(),
            clientOrganization: ClientOrganization? = clientOrganizationCommonTestHelper.buildClientOrganization()
    ): User {
        val user = User(fullName, email, password)
        user.grantClientRole(clientOrganization, UserRole.CLIENT_ADMIN)
        return user
    }
}