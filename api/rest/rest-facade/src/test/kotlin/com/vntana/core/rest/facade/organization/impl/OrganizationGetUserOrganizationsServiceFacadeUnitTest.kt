package com.vntana.core.rest.facade.organization.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.persistence.utils.Executable
import com.vntana.core.rest.facade.organization.AbstractOrganizationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/10/19
 * Time: 11:35 AM
 */
class OrganizationGetUserOrganizationsServiceFacadeUnitTest : AbstractOrganizationServiceFacadeUnitTest() {
    @Test
    fun `test getUserOrganizations when super admin`() {
        // test data
        resetAll()
        val organization = commonTestHelper.buildOrganization()
        val organization2 = commonTestHelper.buildOrganization()
        val user = userHelper.buildUser()
        user.grantSuperAdminRole()
        // expectations
        expect(persistenceUtilityService.runInNewTransaction(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.getByUuid(user.uuid)).andReturn(user)
        expect(organizationService.getAll()).andReturn(listOf(organization, organization2))
        replayAll()
        // test scenario
        organizationServiceFacade.getUserOrganizations(user.uuid).let {
            assertThat(it.totalCount()).isEqualTo(2)
            assertThat(it.items()[0].uuid).isEqualTo(organization.uuid)
            assertThat(it.items()[0].name).isEqualTo(organization.name)
            assertThat(it.items()[0].role).isEqualTo(UserRoleModel.SUPER_ADMIN)
            assertThat(it.items()[1].uuid).isEqualTo(organization2.uuid)
            assertThat(it.items()[1].name).isEqualTo(organization2.name)
            assertThat(it.items()[1].role).isEqualTo(UserRoleModel.SUPER_ADMIN)
        }
        verifyAll()
    }

    @Test
    fun `test getUserOrganizations when is not super admin`() {
        // test data
        resetAll()
        val organization = commonTestHelper.buildOrganization()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val user = userHelper.buildUser(
                clientOrganization = organization
        )
        user.grantClientRole(clientOrganization, UserRole.CLIENT_ADMIN)
        // expectations
        expect(persistenceUtilityService.runInNewTransaction(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.getByUuid(user.uuid)).andReturn(user)
        replayAll()
        // test scenario
        organizationServiceFacade.getUserOrganizations(user.uuid).let {
            assertThat(it.totalCount()).isEqualTo(2)
            assertThat(it.items()[0].uuid).isEqualTo(organization.uuid)
            assertThat(it.items()[0].name).isEqualTo(organization.name)
            assertThat(it.items()[0].role).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
            assertThat(it.items()[1].uuid).isEqualTo(clientOrganization.organization.uuid)
            assertThat(it.items()[1].name).isEqualTo(clientOrganization.organization.name)
            assertThat(it.items()[1].role).isEqualTo(UserRoleModel.CLIENT_ADMIN)
        }
        verifyAll()
    }
}