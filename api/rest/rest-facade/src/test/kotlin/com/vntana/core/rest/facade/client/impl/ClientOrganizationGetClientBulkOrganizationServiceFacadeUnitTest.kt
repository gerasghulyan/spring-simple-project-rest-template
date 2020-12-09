package com.vntana.core.rest.facade.client.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.user.AbstractOrganizationAwareUserRole
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.facade.client.AbstractClientOrganizationServiceFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.anyString
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 4:33 PM
 */
class ClientOrganizationGetClientBulkOrganizationServiceFacadeUnitTest : AbstractClientOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test getByUserAndBulkOrganizations when precondition has error`() {
        resetAll()
        val errorModel = ClientOrganizationErrorResponseModel.USER_NOT_FOUND
        val httpStatusCode = HttpStatus.SC_NOT_FOUND
        expect(preconditionCheckerComponent.checkGetByUserAndBulkOrganizations(anyString(), listOf(anyString()))).andReturn(SingleErrorWithStatus.of(httpStatusCode, errorModel))
        replayAll()
        clientOrganizationServiceFacade.getByUserAndBulkOrganizations(uuid(), listOf(uuid())).let {
            assertBasicErrorResultResponse(it, errorModel)
            assertThat(it.httpStatusCode).isEqualTo(httpStatusCode)
        }
        verifyAll()
    }

    @Test
    fun `test getByUserAndBulkOrganizations for super admin user`() {
        resetAll()
        // Test data
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        user.grantSuperAdminRole()
        val clientOrganization = commonTestHelper.buildClientOrganization(organization = organization)
        organization.grantClientOrganization(clientOrganization)
        expect(preconditionCheckerComponent.checkGetByUserAndBulkOrganizations(anyString(), listOf(anyString()))).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByUuid(anyString())).andReturn(user)
        expect(organizationService.getByUuid(anyString())).andReturn(organization)
        replayAll()
        clientOrganizationServiceFacade.getByUserAndBulkOrganizations(user.uuid, listOf(organization.uuid)).let {
            assertThat(it.response())
            assertThat(it.response().totalCount()).isEqualTo(1)
            val organizationClientOrganization = it.response().items()[0]
            assertThat(organizationClientOrganization.name).isEqualTo(clientOrganization.name)
            assertThat(organizationClientOrganization.slug).isEqualTo(clientOrganization.slug)
            assertThat(organizationClientOrganization.uuid).isEqualTo(clientOrganization.uuid)
            assertThat(organizationClientOrganization.imageBlobId).isEqualTo(clientOrganization.imageBlobId)
            assertThat(organizationClientOrganization.role).isEqualTo(UserRoleModel.SUPER_ADMIN)
            assertThat(organizationClientOrganization.created).isEqualTo(clientOrganization.created)
        }
        verifyAll()
    }

    @Test
    fun `test getByUserAndBulkOrganizations for organization owner user`() {
        resetAll()
        // Test data
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        val clientOrganization = commonTestHelper.buildClientOrganization(organization = organization)
        val userRole = Optional.of(user.roleOfOrganizationOwner(organization).get() as AbstractOrganizationAwareUserRole)
        organization.grantClientOrganization(clientOrganization)
        expect(preconditionCheckerComponent.checkGetByUserAndBulkOrganizations(anyString(), listOf(anyString()))).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByUuid(anyString())).andReturn(user)
        expect(organizationService.getByUuid(anyString())).andReturn(organization)
        expect(userRoleService.findByOrganizationAndUser(organization.uuid, user.uuid)).andReturn(userRole)
        replayAll()
        clientOrganizationServiceFacade.getByUserAndBulkOrganizations(user.uuid, listOf(organization.uuid)).let {
            assertThat(it.response())
            assertThat(it.response().totalCount()).isEqualTo(1)
            val organizationClientOrganization = it.response().items()[0]
            assertThat(organizationClientOrganization.name).isEqualTo(clientOrganization.name)
            assertThat(organizationClientOrganization.slug).isEqualTo(clientOrganization.slug)
            assertThat(organizationClientOrganization.uuid).isEqualTo(clientOrganization.uuid)
            assertThat(organizationClientOrganization.imageBlobId).isEqualTo(clientOrganization.imageBlobId)
            assertThat(organizationClientOrganization.role).isEqualTo(UserRoleModel.ORGANIZATION_OWNER)
            assertThat(organizationClientOrganization.created).isEqualTo(clientOrganization.created)
        }
        verifyAll()
    }

    @Test
    fun `test getByUserAndBulkOrganizations for organization admin user`() {
        resetAll()
        // Test data
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationAdminRole(organization = organization)
        val clientOrganization = commonTestHelper.buildClientOrganization(organization = organization)
        val userRole = Optional.of(user.roleOfOrganizationAdmin(organization).get() as AbstractOrganizationAwareUserRole)
        organization.grantClientOrganization(clientOrganization)
        expect(preconditionCheckerComponent.checkGetByUserAndBulkOrganizations(anyString(), listOf(anyString()))).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByUuid(anyString())).andReturn(user)
        expect(organizationService.getByUuid(anyString())).andReturn(organization)
        expect(userRoleService.findByOrganizationAndUser(organization.uuid, user.uuid)).andReturn(userRole)
        replayAll()
        clientOrganizationServiceFacade.getByUserAndBulkOrganizations(user.uuid, listOf(organization.uuid)).let {
            assertThat(it.response())
            assertThat(it.response().totalCount()).isEqualTo(1)
            val organizationClientOrganization = it.response().items()[0]
            assertThat(organizationClientOrganization.name).isEqualTo(clientOrganization.name)
            assertThat(organizationClientOrganization.slug).isEqualTo(clientOrganization.slug)
            assertThat(organizationClientOrganization.uuid).isEqualTo(clientOrganization.uuid)
            assertThat(organizationClientOrganization.imageBlobId).isEqualTo(clientOrganization.imageBlobId)
            assertThat(organizationClientOrganization.role).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
            assertThat(organizationClientOrganization.created).isEqualTo(clientOrganization.created)
        }
        verifyAll()
    }

    @Test
    fun `test getByUserAndBulkOrganizations for user`() {
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
        expect(preconditionCheckerComponent.checkGetByUserAndBulkOrganizations(anyString(), listOf(anyString()))).andReturn(SingleErrorWithStatus.empty())
        expect(organizationService.getByUuid(anyString())).andReturn(organization)
        expect(userService.getByUuid(anyString())).andReturn(user)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(anyString(), anyString())).andReturn(listOf(clientContentManagerRole, clientOrganizationAdminRole))
        replayAll()
        clientOrganizationServiceFacade.getByUserAndBulkOrganizations(user.uuid, listOf(organization.uuid)).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().items().size).isEqualTo(2)
        }
        verifyAll()
    }
}