package com.vntana.core.rest.facade.organization.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.persistence.utils.Executable
import com.vntana.core.rest.facade.organization.AbstractOrganizationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan.
 * Date: 10/10/19
 * Time: 11:35 AM
 */
class OrganizationGetUserOrganizationsServiceFacadeUnitTest : AbstractOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test getUserOrganizations with illegal arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        restHelper.assertBasicErrorResultResponse(organizationServiceFacade.getUserOrganizations(null), OrganizationErrorResponseModel.MISSING_USER_UUID)
        restHelper.assertBasicErrorResultResponse(organizationServiceFacade.getUserOrganizations(""), OrganizationErrorResponseModel.MISSING_USER_UUID)
        verifyAll()
    }

    @Test
    fun `test getUserOrganizations when super admin`() {
        // test data
        resetAll()
        val organization = commonTestHelper.buildOrganization()
        val organization2 = commonTestHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole()
        user.grantSuperAdminRole()
        val organizations = listOf(organization, organization2)
        // expectations
        expect(persistenceUtilityService.runInNewTransaction(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(organizationService.count()).andReturn(organizations.size.toLong())
        expect(organizationService.getAll(commonTestHelper.buildGetAllOrganizationDto(size = 2))).andReturn(commonTestHelper.buildOrganizationPage(
                organizations = organizations,
                pageAble = commonTestHelper.buildPageRequest(size = organizations.size)
        ))
        replayAll()
        // test scenario
        organizationServiceFacade.getUserOrganizations(user.uuid).let {
            assertThat(it.response().totalCount()).isEqualTo(2)
            assertThat(it.response().items()[0].uuid).isEqualTo(organization.uuid)
            assertThat(it.response().items()[0].slug).isEqualTo(organization.slug)
            assertThat(it.response().items()[0].name).isEqualTo(organization.name)
            assertThat(it.response().items()[0].role).isEqualTo(UserRoleModel.SUPER_ADMIN)
            assertThat(it.response().items()[1].uuid).isEqualTo(organization2.uuid)
            assertThat(it.response().items()[1].name).isEqualTo(organization2.name)
            assertThat(it.response().items()[1].role).isEqualTo(UserRoleModel.SUPER_ADMIN)
            assertThat(it.response().items()[0].created).isEqualTo(organization.created)
        }
        verifyAll()
    }

    @Test
    fun `test getUserOrganizations when is not super admin`() {
        // test data
        resetAll()
        val organization = commonTestHelper.buildOrganization()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(
                organization = organization
        )
        user.grantClientRole(clientOrganization, UserRole.CLIENT_ADMIN)
        // expectations
        expect(persistenceUtilityService.runInNewTransaction(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        replayAll()
        // test scenario
        organizationServiceFacade.getUserOrganizations(user.uuid).let {
            assertThat(it.response().totalCount()).isEqualTo(2)
            assertThat(it.response().items()[0].uuid).isEqualTo(organization.uuid)
            assertThat(it.response().items()[0].name).isEqualTo(organization.name)
            assertThat(it.response().items()[0].role).isEqualTo(UserRoleModel.ORGANIZATION_OWNER)
            assertThat(it.response().items()[1].uuid).isEqualTo(clientOrganization.organization.uuid)
            assertThat(it.response().items()[1].name).isEqualTo(clientOrganization.organization.name)
            assertThat(it.response().items()[1].role).isEqualTo(UserRoleModel.CLIENT_ADMIN)
        }
        verifyAll()
    }

    @Test
    fun `test getUserOrganizations when user not found`() {
        // test data
        resetAll()
        val organization = commonTestHelper.buildOrganization()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(
                organization = organization
        )
        user.grantClientRole(clientOrganization, UserRole.CLIENT_ADMIN)
        // expectations
        expect(persistenceUtilityService.runInNewTransaction(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = organizationServiceFacade.getUserOrganizations(user.uuid)
        restHelper.assertBasicErrorResultResponse(resultResponse, OrganizationErrorResponseModel.USER_NOT_FOUND)
        verifyAll()
    }
}