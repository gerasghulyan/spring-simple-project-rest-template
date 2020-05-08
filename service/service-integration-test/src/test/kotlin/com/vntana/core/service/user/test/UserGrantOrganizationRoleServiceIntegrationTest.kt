package com.vntana.core.service.user.test

import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Geras Ghulyan
 * Date: 18.12.19
 * Time: 13:51
 */
class UserGrantOrganizationRoleServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Autowired
    private lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper

    @Test
    fun `test grantOrganizationRole`() {
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val user = integrationTestHelper.persistUser()
        flushAndClear()
        val dto = integrationTestHelper.buildUserGrantOrganizationRoleDto(
                organizationUuid = organization.uuid,
                uuid = user.uuid,
                role = UserRole.ORGANIZATION_OWNER
        )
        userService.grantOrganizationRole(dto).let {
            flush()
            val resultUser = userService.getByUuid(dto.uuid)
            val roleOfOrganization = resultUser.roleOfOrganization(organization)
            assertThat(roleOfOrganization.isPresent).isTrue()
            assertThat(roleOfOrganization.get().organization).isEqualTo(organization)
            assertThat(roleOfOrganization.get().user).isEqualTo(resultUser)
        }
    }
}