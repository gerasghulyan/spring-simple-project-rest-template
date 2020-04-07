package com.vntana.core.rest.facade.user.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 4/7/2020
 * Time: 2:22 PM
 */
class UserGetByRoleAndOrganizationUuidServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test missing userRole`() {
        resetAll()
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.getByRoleAndOrganizationUuid(null, uuid()), UserErrorResponseModel.MISSING_USER_ROLE)
        verifyAll()
    }

    @Test
    fun `test missing organization uuid`() {
        resetAll()
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.getByRoleAndOrganizationUuid(UserRoleModel.ORGANIZATION_ADMIN, null), UserErrorResponseModel.MISSING_ORGANIZATION)
        assertBasicErrorResultResponse(userServiceFacade.getByRoleAndOrganizationUuid(UserRoleModel.ORGANIZATION_ADMIN, emptyString()), UserErrorResponseModel.MISSING_ORGANIZATION)
        verifyAll()
    }

    @Test
    fun `test when organization not found`() {
        val organizationUuid = uuid()
        resetAll()
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(false)
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.getByRoleAndOrganizationUuid(UserRoleModel.ORGANIZATION_ADMIN, organizationUuid), UserErrorResponseModel.ORGANIZATION_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test not found by organization uuid and user role`() {
        val organizationUuid = uuid()
        val userRole = UserRoleModel.ORGANIZATION_ADMIN
        resetAll()
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(true)
        expect(userService.findByRoleAndOrganizationUuid(UserRole.valueOf(userRole.name), organizationUuid)).andReturn(emptyList())
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.getByRoleAndOrganizationUuid(userRole, organizationUuid), UserErrorResponseModel.NOT_FOUND_FOR_ROLE, UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION)
        verifyAll()
    }

    @Test
    fun `test conflict when organization has more then one Organization Admin`() {
        val organizationUuid = uuid()
        val userRole = UserRoleModel.ORGANIZATION_ADMIN
        resetAll()
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(true)
        expect(userService.findByRoleAndOrganizationUuid(UserRole.valueOf(userRole.name), organizationUuid)).andReturn(listOf(
                userHelper.buildUser(),
                userHelper.buildUser()
        ))
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.getByRoleAndOrganizationUuid(userRole, organizationUuid), UserErrorResponseModel.ORGANIZATION_ROLE_CONFLICT)
        verifyAll()
    }

    @Test
    fun `test find`() {
        val organizationUuid = uuid()
        val userRole = UserRoleModel.ORGANIZATION_ADMIN
        resetAll()
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(true)
        expect(userService.findByRoleAndOrganizationUuid(UserRole.valueOf(userRole.name), organizationUuid)).andReturn(listOf(
                userHelper.buildUser()
        ))
        replayAll()
        assertBasicSuccessResultResponse(userServiceFacade.getByRoleAndOrganizationUuid(userRole, organizationUuid))
        verifyAll()
    }
}