package com.vntana.core.rest.facade.token.personalaccess.impl

import com.vntana.core.model.token.auth.request.TokenAuthenticationRequest
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.token.personalaccess.AbstractPersonalAccessTokenFacadeUnitTest
import org.assertj.core.api.Assertions
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 5:25 PM
 */
class PersonalAccessTokenFindByTokenServiceUnitTest : AbstractPersonalAccessTokenFacadeUnitTest() {

    @Test
    fun `test when token not found`() {
        val token = uuid()
        val request = TokenAuthenticationRequest(token)
        resetAll()
        expect(personalAccessTokenService.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(
            personalAccessTokenServiceFacade.findByToken(request),
            UserErrorResponseModel.TOKEN_NOT_FOUND
        )
    }

    @Test
    fun `test when token found`() {
        val user = userHelper.buildUser()
        val token = uuid()
        val request = TokenAuthenticationRequest(token)
        val pat = testHelper.buildTokenPersonalAccess(token = token, user = user)
        resetAll()
        expect(personalAccessTokenService.findByToken(token)).andReturn(Optional.of(pat))
        replayAll()
        personalAccessTokenServiceFacade.findByToken(request).let {
            assertBasicSuccessResultResponse(it)
            Assertions.assertThat(it.response().userUuid).isEqualTo(user.uuid)
            Assertions.assertThat(it.response().userEmail).isEqualTo(user.email)
        }
    }
}