package com.vntana.core.rest.facade.auth.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.auth.AbstractAuthFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 5:18 PM
 */
class PersonalAccessTokenFindByUserFacadeImplUnitTest : AbstractAuthFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        val user = userHelper.buildUser()
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(
            authFacade.findPersonalAccessTokenByUserUuid(user.uuid),
            UserErrorResponseModel.USER_NOT_FOUND
        )
    }

    @Test
    fun `test when token not found`() {
        val user = userHelper.buildUser()
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(personalAccessTokenService.findByUser(user.uuid)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(
            authFacade.findPersonalAccessTokenByUserUuid(user.uuid),
            UserErrorResponseModel.TOKEN_NOT_FOUND
        )
    }

    @Test
    fun `test when token found`() {
        val user = userHelper.buildUser()
        val token = uuid()
        val pat = personalAccessCommonTestHelper.buildTokenPersonalAccess(token = token, user = user)
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(personalAccessTokenService.findByUser(user.uuid)).andReturn(Optional.of(pat))
        replayAll()
        authFacade.findPersonalAccessTokenByUserUuid(user.uuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().token).isEqualTo(token)
            assertThat(it.response().userUuid).isEqualTo(user.uuid)
        }
    }
}