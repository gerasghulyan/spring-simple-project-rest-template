package com.vntana.core.rest.facade.user.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.persistence.utils.Executable
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import com.vntana.core.service.client.dto.CreateClientOrganizationDto
import com.vntana.core.service.user.dto.UserCreateDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 4:38 PM
 */
class CreateUserServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {
    @Test
    fun `test create`() {
        // test data
        val request = restHelper.buildCreateUserRequest()
        val clientOrganization = clientOrganizationHelper.buildClientOrganization()
        val user = userHelper.buildUser()
        resetAll()
        // expectations
        expect(persistenceUtilityService.runInNewTransaction(isA(Executable::class.java)))
                .andAnswer { (getCurrentArguments()[0] as Executable).execute() }
        expect(clientOrganizationService.create(CreateClientOrganizationDto(request.clientName, request.clientSlug, request.organizationUuid))).andReturn(clientOrganization)
        expect(userService.create(UserCreateDto(
                request.fullName,
                request.email,
                request.password,
                clientOrganization.uuid,
                UserRole.CLIENT_ADMIN
        ))).andReturn(user)
        replayAll()
        // test scenario
        val resultResponse = userServiceFacade.create(request)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid()).isEqualTo(user.uuid)
        verifyAll()
    }
}