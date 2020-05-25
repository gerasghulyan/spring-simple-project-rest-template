package com.vntana.core.rest.facade.auth.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.security.response.model.SecureFindUserByEmailResponseModel
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.model.user.request.FindUserByEmailRequest
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
class FindByEmailAuthFacadeImplUnitTest : AbstractAuthFacadeUnitTest() {

    @Test
    fun `test findByEmail when not found`() {
        // test data
        val user = userHelper.buildUserWithOrganizationOwnerRole()
        resetAll()
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.findByEmail(user.email)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = authFacade.findByEmail(FindUserByEmailRequest(user.email))
        restHelper.assertBasicErrorResultResponse(resultResponse, UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)
        verifyAll()
    }

    @Test
    fun `test findByEmail`() {
        // test data
        val user = userHelper.buildUserWithOrganizationOwnerRole()
        val secureUser = SecureFindUserByEmailResponseModel(user.uuid, user.email, user.password, listOf(UserRoleModel.ORGANIZATION_OWNER))
        resetAll()
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.findByEmail(user.email)).andReturn(Optional.of(user))
        replayAll()
        // test scenario
        authFacade.findByEmail(FindUserByEmailRequest(user.email)).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response()).isEqualTo(secureUser)
        }
        verifyAll()
    }
}