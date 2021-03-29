package com.vntana.core.rest.facade.auth.impl

import com.vntana.core.model.security.request.FindUserByPersonalAccessTokenRequest
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.auth.AbstractAuthFacadeUnitTest
import org.assertj.core.api.Assertions
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 5:25 PM
 */
class FindByPersonalAccessTokenFacadeImplUnitTest : AbstractAuthFacadeUnitTest() {

    @Test
    fun `test when token not found`() {
        val token = uuid()
        val request = FindUserByPersonalAccessTokenRequest(token)
        resetAll()
        expect(personalAccessTokenService.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(
            authFacade.findByPersonalAccessToken(request),
            UserErrorResponseModel.TOKEN_NOT_FOUND
        )
    }

    @Test
    fun `test when token found`() {
        val user = userHelper.buildUser()
        val token = uuid()
        val request = FindUserByPersonalAccessTokenRequest(token)
        val pat = personalAccessCommonTestHelper.buildTokenPersonalAccess(token = token, user = user)
        resetAll()
        expect(personalAccessTokenService.findByToken(token)).andReturn(Optional.of(pat))
        replayAll()
        authFacade.findByPersonalAccessToken(request).let {
            assertBasicSuccessResultResponse(it)
            Assertions.assertThat(it.response().uuid).isEqualTo(user.uuid)
            Assertions.assertThat(it.response().username).isEqualTo(user.email)
        }
    }
}