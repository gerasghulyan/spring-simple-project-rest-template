package com.vntana.core.rest.facade.user.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.persistence.utils.Executable
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 11:09 AM
 */
class UserAccountDetailsServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test when not found`() {
        // test data
        val user = userHelper.buildUser()
        val organization = organizationHelper.buildOrganization()
        resetAll()
        val request = restHelper.buildFindUserByEmailRequest()
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.getByUuid(user.uuid)).andReturn(user)
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        replayAll()
        // test scenario
        val resultResponse = userServiceFacade.accountDetails(user.uuid, organization.uuid)
        restHelper.assertBasicErrorResultResponse(resultResponse, UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION)
        verifyAll()
    }

    @Test
    fun `test when found`() {
        // test data
        val user = userHelper.buildUser()
        val organization = organizationHelper.buildOrganization()
        user.grantOrganizationRole(organization)
        resetAll()
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.getByUuid(user.uuid)).andReturn(user)
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        replayAll()
        // test scenario
        val resultResponse = userServiceFacade.accountDetails(user.uuid, organization.uuid)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid).isEqualTo(user.uuid)
        assertThat(resultResponse.response().email).isEqualTo(user.email)
        assertThat(resultResponse.response().role).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
        assertThat(resultResponse.response().getfullName()).isEqualTo(user.fullName)
        verifyAll()
    }

    @Test
    fun `test when found and role is super admin`() {
        // test data
        val user = userHelper.buildUser()
        val organization = organizationHelper.buildOrganization()
        user.grantSuperAdminRole()
        resetAll()
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.getByUuid(user.uuid)).andReturn(user)
        replayAll()
        // test scenario
        val resultResponse = userServiceFacade.accountDetails(user.uuid, organization.uuid)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid).isEqualTo(user.uuid)
        assertThat(resultResponse.response().email).isEqualTo(user.email)
        assertThat(resultResponse.response().role).isEqualTo(UserRoleModel.SUPER_ADMIN)
        assertThat(resultResponse.response().getfullName()).isEqualTo(user.fullName)
        verifyAll()
    }

}