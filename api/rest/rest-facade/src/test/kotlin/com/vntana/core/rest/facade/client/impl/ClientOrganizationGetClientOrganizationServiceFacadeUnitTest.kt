package com.vntana.core.rest.facade.client.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.persistence.utils.Executable
import com.vntana.core.rest.facade.client.AbstractClientOrganizationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 4:33 PM
 */
class ClientOrganizationGetClientOrganizationServiceFacadeUnitTest : AbstractClientOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test getClientOrganizations when organization owner`() {
        // test data
        resetAll()
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        val clientOrganization = commonTestHelper.buildClientOrganization(organization = organization)
        organization.grantClientOrganization(clientOrganization)
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(organizationService.existsByUuid(organization.uuid)).andReturn(true)
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        replayAll()
        // test scenario
        clientOrganizationServiceFacade.getUserClientOrganizations(user.uuid, organization.uuid).let {
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
    fun `test getClientOrganizations when organization admin`() {
        // test data
        resetAll()
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationAdminRole(organization = organization)
        val clientOrganization = commonTestHelper.buildClientOrganization(organization = organization)
        organization.grantClientOrganization(clientOrganization)
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(organizationService.existsByUuid(organization.uuid)).andReturn(true)
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        replayAll()
        // test scenario
        clientOrganizationServiceFacade.getUserClientOrganizations(user.uuid, organization.uuid).let {
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
    fun `test getClientOrganizations with illegal arguments`() {
        // test data
        resetAll()
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        val clientOrganization = commonTestHelper.buildClientOrganization(organization = organization)
        organization.grantClientOrganization(clientOrganization)
        replayAll()
        // test scenario
        restHelper.assertBasicErrorResultResponse(
                clientOrganizationServiceFacade.getUserClientOrganizations(
                        null,
                        organization.uuid
                ),
                ClientOrganizationErrorResponseModel.MISSING_USER_UUID
        )
        restHelper.assertBasicErrorResultResponse(
                clientOrganizationServiceFacade.getUserClientOrganizations(
                        "",
                        organization.uuid
                ),
                ClientOrganizationErrorResponseModel.MISSING_USER_UUID
        )
        restHelper.assertBasicErrorResultResponse(
                clientOrganizationServiceFacade.getUserClientOrganizations(
                        user.uuid,
                        null
                ),
                ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        restHelper.assertBasicErrorResultResponse(
                clientOrganizationServiceFacade.getUserClientOrganizations(
                        user.uuid,
                        ""
                ),
                ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        verifyAll()
    }

    @Test
    fun `test getClientOrganizations when user not found `() {
        // test data
        resetAll()
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        val clientOrganization = commonTestHelper.buildClientOrganization(organization = organization)
        organization.grantClientOrganization(clientOrganization)
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(organizationService.existsByUuid(organization.uuid)).andReturn(true)
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        restHelper.assertBasicErrorResultResponse(
                clientOrganizationServiceFacade.getUserClientOrganizations(
                        user.uuid,
                        organization.uuid
                ),
                ClientOrganizationErrorResponseModel.USER_NOT_FOUND
        )
        verifyAll()
    }

    @Test
    fun `test getClientOrganizations when organization not found `() {
        // test data
        resetAll()
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        val clientOrganization = commonTestHelper.buildClientOrganization(organization = organization)
        organization.grantClientOrganization(clientOrganization)
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(organizationService.existsByUuid(organization.uuid)).andReturn(false)
        replayAll()
        // test scenario
        restHelper.assertBasicErrorResultResponse(
                clientOrganizationServiceFacade.getUserClientOrganizations(
                        user.uuid,
                        organization.uuid
                ),
                ClientOrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND
        )
        verifyAll()
    }

    @Test
    fun `test getClientOrganizations when super admin`() {
        // test data
        resetAll()
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        user.grantSuperAdminRole()
        val clientOrganization = commonTestHelper.buildClientOrganization(organization = organization)
        organization.grantClientOrganization(clientOrganization)
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(organizationService.existsByUuid(organization.uuid)).andReturn(true)
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        replayAll()
        // test scenario
        clientOrganizationServiceFacade.getUserClientOrganizations(user.uuid, organization.uuid).let {
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
}