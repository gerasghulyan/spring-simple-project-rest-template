package com.vntana.core.rest.facade.token.personalaccess.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.token.personalaccess.AbstractPersonalAccessTokenFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 5:01 PM
 */
class PersonalAccessTokenCreateFacadeServiceUnitTest : AbstractPersonalAccessTokenFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        val user = userHelper.buildUser()
        val token = uuid()
        val dto =
            testHelper.buildCreatePersonalAccessTokenRequest(userUuid = user.uuid, token = token)
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(
            personalAccessTokenServiceFacade.create(dto),
            UserErrorResponseModel.USER_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val user = userHelper.buildUser()
        val token = uuid()
        val req =
            testHelper.buildCreatePersonalAccessTokenRequest(userUuid = user.uuid, token = token)
        val dto =
            testHelper.buildTokenPersonalAccessCreateDto(userUuid = user.uuid, token = token)
        val pat = testHelper.buildTokenPersonalAccess(token = token, user = user)
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(personalAccessTokenService.create(dto)).andReturn(pat)
        replayAll()
        personalAccessTokenServiceFacade.create(req).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().token).isEqualTo(token)
            assertThat(it.response().userUuid).isEqualTo(user.uuid)
        }
    }
}