package com.vntana.core.rest.facade.auth.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.model.user.request.RegeneratePersonalAccessTokenRequest
import com.vntana.core.rest.facade.auth.AbstractAuthFacadeUnitTest
import com.vntana.core.service.token.personalaccess.dto.RegeneratePersonalAccessTokenDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 5:29 PM
 */
class PersonalAccessTokenRegenerateFacadeImplUnitTest : AbstractAuthFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        val user = userHelper.buildUser()
        val token = uuid()
        val request =
            RegeneratePersonalAccessTokenRequest(token, user.uuid)
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(
            authFacade.regeneratePersonalAccessToken(request),
            UserErrorResponseModel.USER_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val user = userHelper.buildUser()
        val token = uuid()
        val request =
            RegeneratePersonalAccessTokenRequest(token, user.uuid)
        val dto = RegeneratePersonalAccessTokenDto(user.uuid, token)
        val pat = personalAccessCommonTestHelper.buildTokenPersonalAccess(token = token, user = user)
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(personalAccessTokenService.regenerateTokenForUser(dto)).andReturn(pat)
        replayAll()
        authFacade.regeneratePersonalAccessToken(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().token).isEqualTo(token)
            assertThat(it.response().userUuid).isEqualTo(user.uuid)
        }
    }
}