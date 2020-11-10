package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 2:13 PM
 */
class UserRoleServiceFindAllByOrganizationUuidServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun `test when noting found`() {
        organizationIntegrationTestHelper.persistOrganization()
        val organization = organizationIntegrationTestHelper.persistOrganization()
        userIntegrationTestHelper.persistUserWithOwnerRole(organizationUuid = organization.uuid)
        flushAndClear()
        userRoleService.findAllByOrganizationUuid(uuid()).let {
            assertThat(it.size).isEqualTo(0)
        }
    }

    @Test
    fun `test only owner role`() {
        organizationIntegrationTestHelper.persistOrganization()
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val user = userIntegrationTestHelper.persistUserWithOwnerRole(organizationUuid = organization.uuid)
        flushAndClear()
        userRoleService.findAllByOrganizationUuid(organization.uuid).let {
            assertThat(it.size).isEqualTo(1)
            assertThat(it[0].user).isEqualTo(user)
            assertThat(it[0].userRole).isEqualTo(UserRole.ORGANIZATION_OWNER)
        }
    }

    @Test
    fun `test owner and client role not exists`() {
        organizationIntegrationTestHelper.persistOrganization()
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = organization.uuid)
        val user = userIntegrationTestHelper.persistUserWithOwnerRole(organizationUuid = organization.uuid)
        user.grantClientRole(clientOrganization, UserRole.CLIENT_CONTENT_MANAGER)
        flushAndClear()
        userRoleService.findAllByOrganizationUuid(organization.uuid).let {
            assertThat(it.size).isEqualTo(1)
            it.map { role -> role.user }.forEach { u -> assertThat(u).isEqualTo(user) }
            assertThat(it.map { role -> role.userRole }.toList()).containsOnly(UserRole.ORGANIZATION_OWNER)
        }
    }

    @Test
    fun `test when client roles not exists`() {
        let {
            val organization1 = organizationIntegrationTestHelper.persistOrganization()
            val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = organization1.uuid)
            val user = userIntegrationTestHelper.persistUserWithOwnerRole(organizationUuid = organization1.uuid)
            user.grantClientRole(clientOrganization, UserRole.CLIENT_ADMIN)
        }
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = organization.uuid)
        val user = userIntegrationTestHelper.persistUserWithOwnerRole(organizationUuid = organization.uuid)
        user.grantClientRole(clientOrganization, UserRole.CLIENT_VIEWER)
        flushAndClear()
        userRoleService.findAllByOrganizationUuid(organization.uuid).let {
            assertThat(it.size).isEqualTo(1)
            it.map { role -> role.user }.forEach { u -> assertThat(u).isEqualTo(user) }
            assertThat(it.map { role -> role.userRole }.toList()).containsOnly(UserRole.ORGANIZATION_OWNER)
        }
    }

    @Test
    fun `test multiple users in one organization and clients not exist`() {
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = organization.uuid)
        val user1 = userIntegrationTestHelper.persistUserWithOwnerRole(organizationUuid = organization.uuid)
        user1.grantClientRole(clientOrganization, UserRole.CLIENT_ADMIN)
        userRoleService.grantOrganizationAdminRole(UserGrantOrganizationRoleDto(user1.uuid, organization.uuid))
        flushAndClear()
        userRoleService.findAllByOrganizationUuid(organization.uuid).let {
            it.map { role -> role.user }.forEach { u -> assertThat(u).isEqualTo(user1) }
            assertThat(it.map { role -> role.userRole }.toList()).containsOnly(
                    UserRole.ORGANIZATION_OWNER,
                    UserRole.ORGANIZATION_ADMIN
            )
        }
    }
}