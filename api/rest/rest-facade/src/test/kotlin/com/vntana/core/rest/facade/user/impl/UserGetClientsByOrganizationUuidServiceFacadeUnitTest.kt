package com.vntana.core.rest.facade.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.anyString
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/7/20
 * Time: 5:04 PM
 */
class UserGetClientsByOrganizationUuidServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val organizationUuid = uuid()
        val error = SingleErrorWithStatus.of(404, UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION)
        expect(preconditionCheckerComponent.checkGetByOrganizationUuid(organizationUuid)).andReturn(error)
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.getClientsByOrganization(organizationUuid), error.error)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val organization = organizationHelper.buildOrganization()
        val clientOrganization1 = clientOrganizationCommonTestHelper.buildClientOrganization(organization = organization)
        val clientOrganization2 = clientOrganizationCommonTestHelper.buildClientOrganization(organization = organization)
        val clientOrganization3 = clientOrganizationCommonTestHelper.buildClientOrganization(organization = organization)
        val user1 = userHelper.buildUser()
        val user2 = userHelper.buildUser()
        val user3 = userHelper.buildUser()
        val clientViewerUser = userRoleCommonTestHelper.buildUserClientViewerRole(user = user1, clientOrganization = clientOrganization1)
        val clientAdminUser = userRoleCommonTestHelper.buildUserClientAdminRole(user = user2, clientOrganization = clientOrganization2)
        val clientContentManagerUser = userRoleCommonTestHelper.buildUserClientContentManagerRole(user = user3, clientOrganization = clientOrganization3)
        expect(preconditionCheckerComponent.checkGetByOrganizationUuid(anyString())).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.findAllClientsByOrganization(anyString())).andReturn(listOf(clientAdminUser, clientContentManagerUser, clientViewerUser))
        replayAll()
        userServiceFacade.getClientsByOrganization(organization.uuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().totalCount()).isEqualTo(3)
        }
        verifyAll()
    }
}