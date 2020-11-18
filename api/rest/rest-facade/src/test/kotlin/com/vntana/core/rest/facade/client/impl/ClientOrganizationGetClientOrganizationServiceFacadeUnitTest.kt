package com.vntana.core.rest.facade.client.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.facade.client.AbstractClientOrganizationServiceFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 4:33 PM
 */
class ClientOrganizationGetClientOrganizationServiceFacadeUnitTest : AbstractClientOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test getUserClientOrganizations when precondition has error`() {
        resetAll()
        val errorModel = ClientOrganizationErrorResponseModel.USER_NOT_FOUND
        val httpStatusCode = HttpStatus.SC_NOT_FOUND
        expect(preconditionCheckerComponent.checkGetUserClientOrganizations(anyString(), anyString())).andReturn(SingleErrorWithStatus.of(httpStatusCode, errorModel))
        replayAll()
        clientOrganizationServiceFacade.getUserClientOrganizations(uuid(), uuid()).let {
            assertBasicErrorResultResponse(it, errorModel)
            assertThat(it.httpStatusCode).isEqualTo(httpStatusCode)
        }
        verifyAll()
    }

    @Test
    fun `test getUserClientOrganizations for super admin user`() {
        resetAll()
        // Test data
        val organization = organizationCommonTestHelper.buildOrganization()
        val organization2 = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        user.grantSuperAdminRole()
        val clientContentManagerRole = userRoleHelper.buildUserClientContentManagerRole(
                clientOrganization = clientOrganizationHelper.buildClientOrganization(organization = organization)
        )
        val clientOrganizationAdminRole = userRoleHelper.buildUserClientAdminRole(
                clientOrganization = clientOrganizationHelper.buildClientOrganization(organization = organization2)
        )
        expect(preconditionCheckerComponent.checkGetUserClientOrganizations(anyString(), anyString())).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByUuid(anyString())).andReturn(user)
        expect(organizationService.getByUuid(anyString())).andReturn(organization)
        expect(userRoleService.findAllClientsByOrganization(anyString())).andReturn(listOf(clientContentManagerRole, clientOrganizationAdminRole))
        replayAll()
        clientOrganizationServiceFacade.getUserClientOrganizations(user.uuid, organization.uuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().items().size).isEqualTo(2)
        }
        verifyAll()
    }

    @Test
    fun `test getUserClientOrganizations for organization owner user`() {
        resetAll()
        // Test data
        val organization = organizationCommonTestHelper.buildOrganization()
        val organization2 = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        val clientContentManagerRole = userRoleHelper.buildUserClientContentManagerRole(
                clientOrganization = clientOrganizationHelper.buildClientOrganization(organization = organization)
        )
        val clientOrganizationAdminRole = userRoleHelper.buildUserClientAdminRole(
                clientOrganization = clientOrganizationHelper.buildClientOrganization(organization = organization2)
        )
        expect(preconditionCheckerComponent.checkGetUserClientOrganizations(anyString(), anyString())).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByUuid(anyString())).andReturn(user)
        expect(organizationService.getByUuid(anyString())).andReturn(organization)
        expect(userRoleService.findAllClientsByOrganization(anyString())).andReturn(listOf(clientContentManagerRole, clientOrganizationAdminRole))
        replayAll()
        clientOrganizationServiceFacade.getUserClientOrganizations(user.uuid, organization.uuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().items().size).isEqualTo(2)
        }
        verifyAll()
    }

    @Test
    fun `test getUserClientOrganizations for organization admin user`() {
        resetAll()
        // Test data
        val organization = organizationCommonTestHelper.buildOrganization()
        val organization2 = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationAdminRole(organization = organization)
        val clientContentManagerRole = userRoleHelper.buildUserClientContentManagerRole(
                clientOrganization = clientOrganizationHelper.buildClientOrganization(organization = organization)
        )
        val clientOrganizationAdminRole = userRoleHelper.buildUserClientAdminRole(
                clientOrganization = clientOrganizationHelper.buildClientOrganization(organization = organization2)
        )
        expect(preconditionCheckerComponent.checkGetUserClientOrganizations(anyString(), anyString())).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByUuid(anyString())).andReturn(user)
        expect(organizationService.getByUuid(anyString())).andReturn(organization)
        expect(userRoleService.findAllClientsByOrganization(anyString())).andReturn(listOf(clientContentManagerRole, clientOrganizationAdminRole))
        replayAll()
        clientOrganizationServiceFacade.getUserClientOrganizations(user.uuid, organization.uuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().items().size).isEqualTo(2)
        }
        verifyAll()
    }

    @Test
    fun `test getUserClientOrganizations for user`() {
        resetAll()
        // Test data
        val organization = organizationCommonTestHelper.buildOrganization()
        val organization2 = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUser()
        val clientContentManagerRole = userRoleHelper.buildUserClientContentManagerRole(
                clientOrganization = clientOrganizationHelper.buildClientOrganization(organization = organization)
        )
        val clientOrganizationAdminRole = userRoleHelper.buildUserClientAdminRole(
                clientOrganization = clientOrganizationHelper.buildClientOrganization(organization = organization2)
        )
        expect(preconditionCheckerComponent.checkGetUserClientOrganizations(anyString(), anyString())).andReturn(SingleErrorWithStatus.empty())
        expect(organizationService.getByUuid(anyString())).andReturn(organization)
        expect(userService.getByUuid(anyString())).andReturn(user)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(anyString(), anyString())).andReturn(listOf(clientContentManagerRole, clientOrganizationAdminRole))
        replayAll()
        clientOrganizationServiceFacade.getUserClientOrganizations(user.uuid, organization.uuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().items().size).isEqualTo(2)
        }
        verifyAll()
    }
}