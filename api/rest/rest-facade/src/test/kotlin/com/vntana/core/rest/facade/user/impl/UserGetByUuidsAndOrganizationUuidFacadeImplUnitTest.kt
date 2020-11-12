package com.vntana.core.rest.facade.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.apache.http.HttpStatus.SC_NOT_FOUND
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 29.09.2020
 * Time: 16:00
 */
class UserGetByUuidsAndOrganizationUuidFacadeImplUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed organization not found`() {
        resetAll()
        val request = restHelper.buildGetByUuidsAndOrganizationUuid()
        val error = SingleErrorWithStatus.of(SC_NOT_FOUND, UserErrorResponseModel.ORGANIZATION_NOT_FOUND)
        expect(preconditionCheckerComponent.checkGetByUuidsAndOrganizationUuid(request)).andReturn(error)
        replayAll()
        userServiceFacade.getByUuidsAndOrganizationUuid(request)
                .apply { assertBasicErrorResultResponse(this, error.error) }
                .apply { assertThat(this.httpStatusCode).isEqualTo(error.httpStatus) }
        verifyAll()
    }
    
    @Test
    fun `test when precondition failed users not found`() {
        resetAll()
        val request = restHelper.buildGetByUuidsAndOrganizationUuid()
        val error = SingleErrorWithStatus.of(SC_NOT_FOUND, UserErrorResponseModel.NOT_FOUND_FOR_USERS_UUIDS)
        expect(preconditionCheckerComponent.checkGetByUuidsAndOrganizationUuid(request)).andReturn(error)
        replayAll()
        userServiceFacade.getByUuidsAndOrganizationUuid(request)
                .apply { assertBasicErrorResultResponse(this, error.error) }
                .apply { assertThat(this.httpStatusCode).isEqualTo(error.httpStatus) }
        verifyAll()
    }

    @Test
    fun `test when user not in organization`() {
        resetAll()
        val organization = organizationHelper.buildOrganization()
        val user1Uuid = uuid()
        val user2Uuid = uuid()
        val request = restHelper.buildGetByUuidsAndOrganizationUuid(uuids = setOf(user1Uuid, user2Uuid), organizationUuid = organization.uuid)
        val userUuids = setOf(user1Uuid, user2Uuid)
        val user1 = userHelper.buildUser(user1Uuid)
        val user2 = userHelper.buildUser(user2Uuid)
        val user1WithOrganizationRole = userRoleCommonTestHelper.buildUserOrganizationAdminRole(user = user1, organization = organization)
        expect(preconditionCheckerComponent.checkGetByUuidsAndOrganizationUuid(request)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.findAllByOrganization(request.organizationUuid)).andReturn(listOf(user1WithOrganizationRole))
        expect(userService.findByUuids(userUuids)).andReturn(setOf(user1, user2))
        replayAll()
        userServiceFacade.getByUuidsAndOrganizationUuid(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().totalCount()).isEqualTo(2)
            assertThat(it.response().items()[0].uuid).isEqualTo(user1.uuid)
            assertThat(it.response().items()[0].fullName).isEqualTo(user1.fullName)
            assertThat(it.response().items()[0].email).isEqualTo(user1.email)
            assertThat(it.response().items()[0].imageBlobId).isEqualTo(user1.imageBlobId)
            assertThat(it.response().items()[0].isInOrganization).isTrue()
            assertThat(it.response().items()[1].uuid).isEqualTo(user2.uuid)
            assertThat(it.response().items()[1].fullName).isEqualTo(user2.fullName)
            assertThat(it.response().items()[1].email).isEqualTo(user2.email)
            assertThat(it.response().items()[1].imageBlobId).isEqualTo(user2.imageBlobId)
            assertThat(it.response().items()[1].isInOrganization).isFalse()
        }
        verifyAll()
    }
    
    @Test
    fun test() {
        resetAll()
        val organization = organizationHelper.buildOrganization()
        val user1Uuid = uuid()
        val user2Uuid = uuid()
        val request = restHelper.buildGetByUuidsAndOrganizationUuid(uuids = setOf(user1Uuid, user2Uuid), organizationUuid = organization.uuid)
        val userUuids = setOf(user1Uuid, user2Uuid)
        val user1 = userHelper.buildUser(user1Uuid)
        val user2 = userHelper.buildUser(user2Uuid)
        val user1WithOrganizationRole = userRoleCommonTestHelper.buildUserOrganizationOwnerRole(user = user1, organization = organization)
        val user2WithOrganizationRole = userRoleCommonTestHelper.buildUserOrganizationAdminRole(user = user2, organization = organization)
        expect(preconditionCheckerComponent.checkGetByUuidsAndOrganizationUuid(request)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.findAllByOrganization(request.organizationUuid)).andReturn(listOf(user1WithOrganizationRole, user2WithOrganizationRole))
        expect(userService.findByUuids(userUuids)).andReturn(setOf(user1, user2))
        replayAll()
        userServiceFacade.getByUuidsAndOrganizationUuid(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().totalCount()).isEqualTo(2)
            assertThat(it.response().items()[0].uuid).isEqualTo(user1.uuid)
            assertThat(it.response().items()[0].fullName).isEqualTo(user1.fullName)
            assertThat(it.response().items()[0].email).isEqualTo(user1.email)
            assertThat(it.response().items()[0].imageBlobId).isEqualTo(user1.imageBlobId)
            assertThat(it.response().items()[0].isInOrganization).isTrue()
            assertThat(it.response().items()[1].uuid).isEqualTo(user2.uuid)
            assertThat(it.response().items()[1].fullName).isEqualTo(user2.fullName)
            assertThat(it.response().items()[1].email).isEqualTo(user2.email)
            assertThat(it.response().items()[1].imageBlobId).isEqualTo(user2.imageBlobId)
            assertThat(it.response().items()[1].isInOrganization).isTrue()
        }
        verifyAll()
    }
}