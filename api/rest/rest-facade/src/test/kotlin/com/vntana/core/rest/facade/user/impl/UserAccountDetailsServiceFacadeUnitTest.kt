package com.vntana.core.rest.facade.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 11:09 AM
 */
class UserAccountDetailsServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val userUuid = uuid()
        val httpStatusCode = randomInt()
        val errorModel = UserErrorResponseModel.MISSING_UUID
        expect(preconditionCheckerComponent.checkAccountDetails(userUuid)).andReturn(SingleErrorWithStatus.of(httpStatusCode, errorModel))
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.accountDetails(userUuid), errorModel)
        verifyAll()
    }

    @Test
    fun `test when super admin`() {
        resetAll()
        val userUuid = uuid()
        val user = userHelper.buildUser()
        user.grantSuperAdminRole()
        expect(preconditionCheckerComponent.checkAccountDetails(userUuid)).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByUuid(userUuid)).andReturn(user)
        replayAll()
        userServiceFacade.accountDetails(userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(user.uuid)
            assertThat(it.response().fullName).isEqualTo(user.fullName)
            assertThat(it.response().email).isEqualTo(user.email)
            assertThat(it.response().roles.superAdmin).isTrue()
            assertThat(it.response().roles.ownerInOrganization.size).isEqualTo(1)
            assertThat(it.response().isEmailVerified).isEqualTo(user.verified)
            assertThat(it.response().imageBlobId).isEqualTo(user.imageBlobId)
        }
        verifyAll()
    }

    @Test
    fun `test when is not super admin`() {
        resetAll()
        val userUuid = uuid()
        val user = userHelper.buildUser()
        expect(preconditionCheckerComponent.checkAccountDetails(userUuid)).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByUuid(userUuid)).andReturn(user)
        replayAll()
        userServiceFacade.accountDetails(userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(user.uuid)
            assertThat(it.response().fullName).isEqualTo(user.fullName)
            assertThat(it.response().email).isEqualTo(user.email)
            assertThat(it.response().roles.superAdmin).isFalse()
            assertThat(it.response().roles.ownerInOrganization.size).isEqualTo(1)
            assertThat(it.response().isEmailVerified).isEqualTo(user.verified)
            assertThat(it.response().imageBlobId).isEqualTo(user.imageBlobId)
        }
        verifyAll()
    }

    @Test
    fun `test when is organization owner in 2 organizations`() {
        resetAll()
        val userUuid = uuid()
        val user = userHelper.buildUser()
        val organization1 = organizationHelper.buildOrganization()
        val organization2 = organizationHelper.buildOrganization()
        user.grantOrganizationRole(organization1)
        user.grantOrganizationRole(organization2)
        expect(preconditionCheckerComponent.checkAccountDetails(userUuid)).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByUuid(userUuid)).andReturn(user)
        replayAll()
        userServiceFacade.accountDetails(userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(user.uuid)
            assertThat(it.response().fullName).isEqualTo(user.fullName)
            assertThat(it.response().email).isEqualTo(user.email)
            assertThat(it.response().roles.superAdmin).isFalse()
            assertThat(it.response().roles.ownerInOrganization.size).isEqualTo(3)
            assertThat(it.response().roles.ownerInOrganization).contains(organization1.uuid, organization2.uuid)
            assertThat(it.response().isEmailVerified).isEqualTo(user.verified)
            assertThat(it.response().imageBlobId).isEqualTo(user.imageBlobId)
        }
        verifyAll()
    }
}