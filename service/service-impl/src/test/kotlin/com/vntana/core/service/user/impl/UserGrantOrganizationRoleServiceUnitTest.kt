package com.vntana.core.service.user.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan.
 * Date: 10/4/19
 * Time: 11:22 AM
 */
class UserGrantOrganizationRoleServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userService.grantOrganizationRole(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.grantOrganizationRole(helper.buildUserGrantOrganizationRoleDto(uuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.grantOrganizationRole(helper.buildUserGrantOrganizationRoleDto(uuid = "")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.grantOrganizationRole(helper.buildUserGrantOrganizationRoleDto(organizationUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.grantOrganizationRole(helper.buildUserGrantOrganizationRoleDto(organizationUuid = "")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.grantOrganizationRole(helper.buildUserGrantOrganizationRoleDto(role = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test grantOrganizationRole`() {
        // test data
        resetAll()
        val organization = organizationHelper.buildOrganization()
        val user = helper.buildUser()
        val dto = helper.buildUserGrantOrganizationRoleDto(
                uuid = user.uuid,
                organizationUuid = organization.uuid,
                role = UserRole.ORGANIZATION_OWNER
        )
        // expectations
        expect(userRepository.findByUuid(dto.uuid)).andReturn(Optional.of(user))
        expect(organizationService.getByUuid(dto.organizationUuid)).andReturn(organization)
        replayAll()
        // test scenario
        userService.grantOrganizationRole(dto)
        verifyAll()
    }
}