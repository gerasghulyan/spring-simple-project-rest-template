package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserOrganizationOwnerRole
import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/8/20
 * Time: 4:55 PM
 */
class UserRoleGrantOrganizationOwnerRoleServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userRoleService.grantOrganizationOwnerRole(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.grantOrganizationOwnerRole(commonTestHelper.buildUserGrantOrganizationRoleDto(userUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.grantOrganizationOwnerRole(commonTestHelper.buildUserGrantOrganizationRoleDto(userUuid = "")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.grantOrganizationOwnerRole(commonTestHelper.buildUserGrantOrganizationRoleDto(organizationUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.grantOrganizationOwnerRole(commonTestHelper.buildUserGrantOrganizationRoleDto(organizationUuid = "")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test grantOrganizationOwnerRole`() {
        resetAll()
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userCommonTestHelper.buildUser()
        val dto = commonTestHelper.buildUserGrantOrganizationRoleDto(
                userUuid = user.uuid,
                organizationUuid = organization.uuid
        )
        expect(userService.getByUuid(dto.userUuid)).andReturn(user)
        expect(organizationService.getByUuid(dto.organizationUuid)).andReturn(organization)
        expect(userRoleRepository.save(isA(UserOrganizationOwnerRole::class.java))).andAnswer { getCurrentArguments()[0] as UserOrganizationOwnerRole }
        replayAll()
        userRoleService.grantOrganizationOwnerRole(dto).let {
            assertThat(it.user).isEqualTo(user)
            assertThat(it.organization).isEqualTo(organization)
            assertThat(it.userRole).isEqualTo(UserRole.ORGANIZATION_OWNER)
        }
        verifyAll()
    }
}