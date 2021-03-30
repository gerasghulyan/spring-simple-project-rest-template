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
 * Time: 5:01 PM
 */
class CreatePersonalAccessTokenFacadeImplUnitTest : AbstractAuthFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        val user = userHelper.buildUser()
        val token = uuid()
        val dto =
            personalAccessCommonTestHelper.buildCreatePersonalAccessTokenRequest(userUuid = user.uuid, token = token)
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(
            authFacade.createPersonalAccessToken(dto),
            UserErrorResponseModel.USER_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val user = userHelper.buildUser()
        val token = uuid()
        val req =
            personalAccessCommonTestHelper.buildCreatePersonalAccessTokenRequest(userUuid = user.uuid, token = token)
        val dto =
            personalAccessCommonTestHelper.buildTokenPersonalAccessCreateDto(userUuid = user.uuid, token = token)
        val pat = personalAccessCommonTestHelper.buildTokenPersonalAccess(token = token, user = user)
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(personalAccessTokenService.create(dto)).andReturn(pat)
        replayAll()
        authFacade.createPersonalAccessToken(req).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().token).isEqualTo(token)
            assertThat(it.response().userUuid).isEqualTo(user.uuid)
        }
    }
}