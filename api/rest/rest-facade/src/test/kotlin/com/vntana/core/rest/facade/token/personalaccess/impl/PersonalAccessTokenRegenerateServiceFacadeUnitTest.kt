package com.vntana.core.rest.facade.token.personalaccess.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.model.user.request.RegeneratePersonalAccessKeyTokenRequest
import com.vntana.core.rest.facade.token.personalaccess.AbstractPersonalAccessTokenFacadeUnitTest
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
class PersonalAccessTokenRegenerateServiceFacadeUnitTest : AbstractPersonalAccessTokenFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        val user = userHelper.buildUser()
        val token = uuid()
        val request = RegeneratePersonalAccessKeyTokenRequest(token, user.uuid)
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(
            personalAccessTokenServiceFacade.regenerate(request),
            UserErrorResponseModel.USER_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val user = userHelper.buildUser()
        val token = uuid()
        val request = RegeneratePersonalAccessKeyTokenRequest(token, user.uuid)
        val dto = RegeneratePersonalAccessTokenDto(user.uuid, token)
        val pat = testHelper.buildTokenPersonalAccess(token = token, user = user)
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(personalAccessTokenService.regenerateTokenForUser(dto)).andReturn(pat)
        replayAll()
        personalAccessTokenServiceFacade.regenerate(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().token).isEqualTo(token)
            assertThat(it.response().userUuid).isEqualTo(user.uuid)
        }
    }
}