package com.vntana.core.service.organization.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 3/9/2020
 * Time: 3:29 PM
 */
class OrganizationGetUserOrganizationsByUserUuidAndRoleServiceUnitTest : AbstractOrganizationServiceUnitTest() {

    @Test
    fun `test get with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { organizationService.getUserOrganizationsByUserUuidAndRole(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.getUserOrganizationsByUserUuidAndRole(helper.buildGetUserOrganizationsByUserUuidAndRoleDto(userUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.getUserOrganizationsByUserUuidAndRole(helper.buildGetUserOrganizationsByUserUuidAndRoleDto(userUuid = StringUtils.EMPTY)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.getUserOrganizationsByUserUuidAndRole(helper.buildGetUserOrganizationsByUserUuidAndRoleDto(userRole = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `get organizations by user uuid and role`() {
        // test data
        val userUuid = uuid()
        val organization = helper.buildOrganization()
        resetAll()
        // expectations
        expect(organizationRepository.findUserOrganizationsByUserUuidAndRole(userUuid, UserRole.ORGANIZATION_ADMIN.name)).andReturn(listOf(organization))
        replayAll()
        // test scenario
        organizationService.getUserOrganizationsByUserUuidAndRole(helper.buildGetUserOrganizationsByUserUuidAndRoleDto(userUuid = userUuid))
                .let {
                    Assertions.assertThat(it).isNotEmpty
                    Assertions.assertThat(it.size).isEqualTo(1)
                    Assertions.assertThat(it).contains(organization)
                }
        verifyAll()
    }
}