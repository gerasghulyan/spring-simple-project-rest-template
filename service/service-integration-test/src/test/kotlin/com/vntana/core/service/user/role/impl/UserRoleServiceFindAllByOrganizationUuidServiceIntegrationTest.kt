package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
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
        organizationIntegrationTest.persistOrganization()
        val organization = organizationIntegrationTest.persistOrganization()
        integrationTestHelper.persistUser(organizationUuid = organization.uuid)
        flushAndClear()
        userRoleService.findAllByOrganizationUuid(uuid()).let {
            assertThat(it.size).isEqualTo(0)
        }
    }

    @Test
    fun `test only owner role`() {
        organizationIntegrationTest.persistOrganization()
        val organization = organizationIntegrationTest.persistOrganization()
        val user = integrationTestHelper.persistUser(organizationUuid = organization.uuid)
        flushAndClear()
        userRoleService.findAllByOrganizationUuid(organization.uuid).let {
            assertThat(it.size).isEqualTo(1)
            assertThat(it[0].user).isEqualTo(user)
            assertThat(it[0].userRole).isEqualTo(UserRole.ORGANIZATION_OWNER)
        }
    }

    @Test
    fun `test owner and client role`() {
        organizationIntegrationTest.persistOrganization()
        val organization = organizationIntegrationTest.persistOrganization()
        val clientOrganization = clientOrganizationIntegrationTest.persistClientOrganization(organizationUuid = organization.uuid)
        val user = integrationTestHelper.persistUser(organizationUuid = organization.uuid)
        user.grantClientRole(clientOrganization, UserRole.CLIENT_ADMIN)
        flushAndClear()
        userRoleService.findAllByOrganizationUuid(organization.uuid).let {
            assertThat(it.size).isEqualTo(2)
            it.map { role -> role.user }.forEach { u -> assertThat(u).isEqualTo(user) }
            assertThat(it.map { role -> role.userRole }.toList()).containsOnly(UserRole.CLIENT_ADMIN, UserRole.ORGANIZATION_OWNER)
        }
    }

    @Test
    fun `test when other user with roles in other organization exists`() {
        let {
            val organization1 = organizationIntegrationTest.persistOrganization()
            val clientOrganization = clientOrganizationIntegrationTest.persistClientOrganization(organizationUuid = organization1.uuid)
            val user = integrationTestHelper.persistUser(organizationUuid = organization1.uuid)
            user.grantClientRole(clientOrganization, UserRole.CLIENT_ADMIN)
        }
        val organization = organizationIntegrationTest.persistOrganization()
        val clientOrganization = clientOrganizationIntegrationTest.persistClientOrganization(organizationUuid = organization.uuid)
        val user = integrationTestHelper.persistUser(organizationUuid = organization.uuid)
        user.grantClientRole(clientOrganization, UserRole.CLIENT_ADMIN)
        flushAndClear()
        userRoleService.findAllByOrganizationUuid(organization.uuid).let {
            assertThat(it.size).isEqualTo(2)
            it.map { role -> role.user }.forEach { u -> assertThat(u).isEqualTo(user) }
            assertThat(it.map { role -> role.userRole }.toList()).containsOnly(UserRole.CLIENT_ADMIN, UserRole.ORGANIZATION_OWNER)
        }
    }

    @Test
    fun `test multiple users in one organization`() {
        val organization = organizationIntegrationTest.persistOrganization()
        val clientOrganization = clientOrganizationIntegrationTest.persistClientOrganization(organizationUuid = organization.uuid)
        val user1 = integrationTestHelper.persistUser(organizationUuid = organization.uuid)
        //TODO("add new user with admin role")
        user1.grantClientRole(clientOrganization, UserRole.CLIENT_ADMIN)
        flushAndClear()
        userRoleService.findAllByOrganizationUuid(organization.uuid).let {
            it.map { role -> role.user }.forEach { u -> assertThat(u).isEqualTo(user1) }
            assertThat(it.map { role -> role.userRole }.toList()).containsOnly(UserRole.CLIENT_ADMIN, UserRole.ORGANIZATION_OWNER)
        }
    }
}