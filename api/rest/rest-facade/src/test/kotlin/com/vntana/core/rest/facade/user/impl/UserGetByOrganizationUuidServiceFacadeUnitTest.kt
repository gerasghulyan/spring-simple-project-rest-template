package com.vntana.core.rest.facade.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 5:04 PM
 */
class UserGetByOrganizationUuidServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val organizationUuid = uuid()
        val error = SingleErrorWithStatus.of(404, UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION)
        expect(preconditionCheckerComponent.checkGetByOrganizationUuid(organizationUuid)).andReturn(error)
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.getByOrganizationUuid(organizationUuid), error.error)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val organization = organizationHelper.buildOrganization()
        val organizationUuid = organization.uuid
        val user = userHelper.buildUserWithOrganizationOwnerRole()
        val organizationRole = userRoleCommonTestHelper.buildUserOrganizationOwnerRole(user = user)
        val clientAdminRole = userRoleCommonTestHelper.buildUserClientAdminRole(user = user)
        expect(preconditionCheckerComponent.checkGetByOrganizationUuid(organizationUuid)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.findAllByOrganizationUuid(organizationUuid)).andReturn(listOf(organizationRole, clientAdminRole))
        replayAll()
        userServiceFacade.getByOrganizationUuid(organizationUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().totalCount()).isEqualTo(2)
            assertThat(it.response().items()[0].uuid).isEqualTo(organizationRole.user.uuid)
            assertThat(it.response().items()[0].userRoleModel).isEqualTo(UserRoleModel.valueOf(organizationRole.userRole.name))
            assertThat(it.response().items()[0].email).isEqualTo(organizationRole.user.email)
            assertThat(it.response().items()[0].fullName).isEqualTo(organizationRole.user.fullName)
            assertThat(it.response().items()[0].imageBlobId).isEqualTo(organizationRole.user.imageBlobId)
            assertThat(it.response().items()[1].uuid).isEqualTo(clientAdminRole.user.uuid)
            assertThat(it.response().items()[1].userRoleModel).isEqualTo(UserRoleModel.valueOf(clientAdminRole.userRole.name))
            assertThat(it.response().items()[1].email).isEqualTo(clientAdminRole.user.email)
            assertThat(it.response().items()[1].fullName).isEqualTo(clientAdminRole.user.fullName)
            assertThat(it.response().items()[1].imageBlobId).isEqualTo(clientAdminRole.user.imageBlobId)
        }
        verifyAll()
    }
}