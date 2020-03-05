package com.vntana.core.rest.facade.user.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.persistence.utils.Executable
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import com.vntana.core.service.organization.dto.CreateOrganizationDto
import com.vntana.core.service.user.dto.CreateUserDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 4:38 PM
 */
class UserCreateServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {
    @Test
    fun `test create`() {
        // test data
        val request = restHelper.buildCreateUserRequest()
        val organization = organizationHelper.buildOrganization()
        val user = userHelper.buildUser()
        resetAll()
        // expectations
        expect(emailValidationComponent.isValid(request.email))
                .andReturn(true)
        expect(userService.findByEmail(request.email))
                .andReturn(Optional.empty())
        expect(persistenceUtilityService.runInNewTransaction(isA(Executable::class.java)))
                .andAnswer { (getCurrentArguments()[0] as Executable).execute() }
        expect(organizationService.create(CreateOrganizationDto(request.organizationName, request.organizationSlug, null))).andReturn(organization)
        expect(userService.create(CreateUserDto(
                request.fullName,
                request.email,
                request.password,
                organization.uuid,
                UserRole.ORGANIZATION_ADMIN
        ))).andReturn(user)
        expect(organizationLifecycleMediator.onCreated(organization))
        replayAll()
        // test scenario
        val resultResponse = userServiceFacade.create(request)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid).isEqualTo(user.uuid)
        assertThat(resultResponse.response().organizationUuid).isEqualTo(organization.uuid)
        verifyAll()
    }

    @Test
    fun `test create user with existing email`() {
        val request = restHelper.buildCreateUserRequest()
        val user = userHelper.buildUser()
        resetAll()
        // expectations
        expect(emailValidationComponent.isValid(request.email))
                .andReturn(true)
        expect(userService.findByEmail(request.email))
                .andReturn(Optional.of(user))
        replayAll()
        //test scenario
        userServiceFacade.create(request).let {
            restHelper.assertBasicErrorResultResponse(it, UserErrorResponseModel.SIGN_UP_WITH_EXISTING_EMAIL)
        }
        verifyAll()
    }

    @Test
    fun `test create user by invalid email`() {
        val request = restHelper.buildCreateUserRequest(email = userHelper.buildUserInvalidEmail())
        resetAll()
        // expectations
        expect(emailValidationComponent.isValid(request.email))
                .andReturn(false)
        replayAll()
        //test scenario
        userServiceFacade.create(request).let {
            restHelper.assertBasicErrorResultResponse(it, UserErrorResponseModel.INVALID_EMAIL_FORMAT)
        }
        verifyAll()
    }
}