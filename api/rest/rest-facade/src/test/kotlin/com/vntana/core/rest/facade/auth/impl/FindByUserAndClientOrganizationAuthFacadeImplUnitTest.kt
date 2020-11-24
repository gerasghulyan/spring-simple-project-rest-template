package com.vntana.core.rest.facade.auth.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.security.request.FindUserByUuidAndClientOrganizationRequest
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.auth.AbstractAuthFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.anyString
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan
 * Date: 08.11.19
 * Time: 11:42
 */
class FindByUserAndClientOrganizationAuthFacadeImplUnitTest : AbstractAuthFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        resetAll()
        val userUuid = uuid()
        expect(userService.findByUuid(userUuid)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(
                authFacade.findByUserAndClientOrganization(userResourceTestHelper.buildFindUserByUuidAndClientOrganizationRequest(userUuid, uuid())),
                UserErrorResponseModel.USER_NOT_FOUND
        )
        verifyAll()
    }

    @Test
    fun `test when client not found`() {
        resetAll()
        val user = userHelper.buildUser()
        expect(userService.findByUuid(anyString())).andReturn(Optional.of(user))
        expect(userRoleService.findByClientOrganizationAndUser(anyString(), anyString())).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(
                authFacade.findByUserAndClientOrganization(userResourceTestHelper.buildFindUserByUuidAndClientOrganizationRequest()),
                UserErrorResponseModel.NOT_FOUND_FOR_ROLE
        )
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val user = userHelper.buildUser()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        user.grantClientRole(clientOrganization, UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        val clientManager = userRoleCommonTestHelper.buildUserClientContentManagerRole(user = user, clientOrganization = clientOrganization)
        expect(userService.findByUuid(anyString())).andReturn(Optional.of(user))
        expect(userRoleService.findByClientOrganizationAndUser(anyString(), anyString())).andReturn(Optional.of(clientManager))
        replayAll()
        authFacade.findByUserAndClientOrganization(userResourceTestHelper.buildFindUserByUuidAndClientOrganizationRequest()).let {
            assertBasicSuccessResultResponse(it)
            val response = it.response()
            assertThat(response.clientUuid).isEqualTo(clientOrganization.uuid)
            assertThat(response.organizationUuid).isEqualTo(clientOrganization.organization.uuid)
            assertThat(response.userRole).isEqualTo(UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
            assertThat(response.username).isEqualTo(user.email)
            assertThat(response.uuid).isEqualTo(user.uuid)
            assertThat(response.superAdmin).isEqualTo(false)
        }
        verifyAll()
    }
}