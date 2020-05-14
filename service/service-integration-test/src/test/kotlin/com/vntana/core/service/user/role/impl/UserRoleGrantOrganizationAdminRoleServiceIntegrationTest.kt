package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/8/20
 * Time: 5:13 PM
 */
class UserRoleGrantOrganizationAdminRoleServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun `test grantOrganizationAdminRole`() {
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val user = userIntegrationTestHelper.persistUser()
        flushAndClear()
        val dto = integrationTestHelper.buildUserGrantOrganizationRoleDto(
                organizationUuid = organization.uuid,
                userUuid = user.uuid
        )
        userRoleService.grantOrganizationAdminRole(dto).let {
            flushAndClear()
            val resultUser = userService.getByUuid(dto.userUuid)
            assertThat(resultUser.roles()).contains(it)
        }
    }

}