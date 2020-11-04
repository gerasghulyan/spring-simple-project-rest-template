package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import com.vntana.core.service.user.role.dto.UserClientRole
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.11.2020
 * Time: 11:40
 */
class UserRoleGrantClientRoleServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun test() {
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val user = userIntegrationTestHelper.persistUser()
        flushAndClear()
        val dto = integrationTestHelper.buildUserGrantClientRoleDto(
                clientOrganizationUuid = clientOrganization.uuid,
                clientRole = UserClientRole.CLIENT_ADMIN,
                userUuid = user.uuid
        )
        userRoleService.grantClientRole(dto).let {
            flushAndClear()
            val resultUser = userService.getByUuid(dto.userUuid)
            assertThat(resultUser.roles()).contains(it)
        }
    }
}