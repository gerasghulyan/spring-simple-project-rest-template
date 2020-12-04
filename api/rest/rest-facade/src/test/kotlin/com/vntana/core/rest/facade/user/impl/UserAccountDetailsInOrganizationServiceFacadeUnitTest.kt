package com.vntana.core.rest.facade.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.12.2020
 * Time: 14:04
 */
class UserAccountDetailsInOrganizationServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val userUuid = uuid()
        val organizationUuid = uuid()
        val httpStatusCode = randomInt()
        val errorModel = UserErrorResponseModel.MISSING_UUID
        expect(preconditionCheckerComponent.checkAccountDetails(organizationUuid, userUuid)).andReturn(SingleErrorWithStatus.of(httpStatusCode, errorModel))
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.accountDetails(organizationUuid, userUuid), errorModel)
        verifyAll()
    }

    @Test
    fun `test when super admin`() {
        resetAll()
        val userUuid = uuid()
        val organization = organizationHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        user.grantSuperAdminRole()
        expect(preconditionCheckerComponent.checkAccountDetails(organization.uuid, userUuid)).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByUuid(userUuid)).andReturn(user)
        replayAll()
        userServiceFacade.accountDetails(organization.uuid, userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(user.uuid)
            assertThat(it.response().fullName).isEqualTo(user.fullName)
            assertThat(it.response().email).isEqualTo(user.email)
            assertThat(it.response().userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_OWNER)
            assertThat(it.response().isEmailVerified).isEqualTo(user.verified)
            assertThat(it.response().imageBlobId).isEqualTo(user.imageBlobId)
        }
        verifyAll()
    }

    @Test
    fun `test when is not super admin`() {
        resetAll()
        val userUuid = uuid()
        val organization = organizationHelper.buildOrganization()
        val user = userHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        expect(preconditionCheckerComponent.checkAccountDetails(organization.uuid, userUuid)).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByUuid(userUuid)).andReturn(user)
        replayAll()
        userServiceFacade.accountDetails(organization.uuid, userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(user.uuid)
            assertThat(it.response().fullName).isEqualTo(user.fullName)
            assertThat(it.response().email).isEqualTo(user.email)
            assertThat(it.response().userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_OWNER)
            assertThat(it.response().isEmailVerified).isEqualTo(user.verified)
            assertThat(it.response().imageBlobId).isEqualTo(user.imageBlobId)
        }
        verifyAll()
    }

    @Test
    fun `test when is client member in 2 clients`() {
        resetAll()
        val userUuid = uuid()
        val user = userHelper.buildUserWithOrganizationOwnerRole()
        val organization = organizationHelper.buildOrganization()
        val clientOrganization1 = clientOrganizationCommonTestHelper.buildClientOrganization(organization = organization)
        val clientOrganization2 = clientOrganizationCommonTestHelper.buildClientOrganization(organization = organization)
        user.grantClientRole(clientOrganization1, UserRole.CLIENT_ORGANIZATION_ADMIN)
        user.grantClientRole(clientOrganization2, UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        expect(preconditionCheckerComponent.checkAccountDetails(organization.uuid, userUuid)).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByUuid(userUuid)).andReturn(user)
        replayAll()
        userServiceFacade.accountDetails(organization.uuid, userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(user.uuid)
            assertThat(it.response().fullName).isEqualTo(user.fullName)
            assertThat(it.response().email).isEqualTo(user.email)
            assertThat(it.response().userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_CLIENT_MEMBER)
            assertThat(it.response().isEmailVerified).isEqualTo(user.verified)
            assertThat(it.response().imageBlobId).isEqualTo(user.imageBlobId)
        }
        verifyAll()
    }
}