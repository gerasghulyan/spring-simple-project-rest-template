package com.vntana.core.rest.facade.auth.impl

import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest
import com.vntana.core.model.security.response.model.SecureFindUserByEmailResponseModel
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.persistence.utils.Executable
import com.vntana.core.rest.facade.auth.AbstractAuthFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan
 * Date: 08.11.19
 * Time: 11:42
 */
class FindByUserAndOrganizaitonAuthFacadeImplUnitTest : AbstractAuthFacadeUnitTest() {

    @Test
    fun `test findByUserAndOrganization when not found`() {
        // test data
        val user = userHelper.buildUser()
        val organization = organizationCommonTestHelper.buildOrganization()
        val secureUser = SecureFindUserByEmailResponseModel(user.uuid, user.email, user.password)
        resetAll()
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.findByEmail(user.email)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = authFacade.findByUserAndOrganization(
                FindUserByUuidAndOrganizationRequest(user.uuid, organization.uuid)
        )
        restHelper.assertBasicErrorResultResponse(resultResponse, UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)
        verifyAll()
    }

    @Test
    fun `test findByUserAndOrganization`() {
        // test data
        val user = userHelper.buildUser()
        val secureUser = SecureFindUserByEmailResponseModel(user.uuid, user.email, user.password)
        val organization = organizationCommonTestHelper.buildOrganization()
        resetAll()
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.getByUuid(user.uuid)).andReturn(user)
        replayAll()
        // test scenario
        val resultResponse = authFacade.findByUserAndOrganization(
                FindUserByUuidAndOrganizationRequest(user.email, organization.uuid)
        )
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.success()).isTrue()
        assertThat(resultResponse.errors()).isEmpty()
        assertThat(resultResponse.response()).isEqualTo(secureUser)
        verifyAll()
    }
}