package com.vntana.core.rest.facade.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/7/20
 * Time: 5:04 PM
 */
class UserGetByClientOrganizationUuidServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val clientUuid = uuid()
        val error = SingleErrorWithStatus.of(404, UserErrorResponseModel.NOT_FOUND_FOR_CLIENT_ORGANIZATION)
        expect(preconditionCheckerComponent.checkGetByClientOrganizationUuid(clientUuid)).andReturn(error)
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.getByClientOrganizationUuid(clientUuid), error.error)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val clientUuid = clientOrganization.uuid
        val user1 = userHelper.buildUser()
        val user2 = userHelper.buildUser()
        val user3 = userHelper.buildUser()
        val clientViewerUser = userRoleCommonTestHelper.buildUserClientViewerRole(user = user1, clientOrganization = clientOrganization)
        val clientAdminUser = userRoleCommonTestHelper.buildUserClientAdminRole(user = user2, clientOrganization = clientOrganization)
        val clientContentManagerUser = userRoleCommonTestHelper.buildUserClientContentManagerRole(user = user3, clientOrganization = clientOrganization)
        expect(preconditionCheckerComponent.checkGetByClientOrganizationUuid(clientUuid)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.findAllByClientOrganization(clientUuid)).andReturn(listOf(clientAdminUser, clientContentManagerUser, clientViewerUser))
        replayAll()
        userServiceFacade.getByClientOrganizationUuid(clientUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().totalCount()).isEqualTo(3)
        }
        verifyAll()
    }
}