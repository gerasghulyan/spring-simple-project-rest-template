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
 * Time: 5:14 PM
 */
class PersonalAccessTokenExpireServiceFacadeUnitTest : AbstractPersonalAccessTokenFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        val user = userHelper.buildUser()
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(
            personalAccessTokenServiceFacade.expireByUserUuid(user.uuid),
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
        personalAccessTokenServiceFacade.expireByUserUuid(user.uuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().token).isEqualTo(null)
            assertThat(it.response().userUuid).isEqualTo(user.uuid)
        }
    }

    @Test
    fun `test when token found`() {
        val user = userHelper.buildUser()
        val token = uuid()
        val pat = testHelper.buildTokenPersonalAccess(token = token, user = user)
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(personalAccessTokenService.findByUser(user.uuid)).andReturn(Optional.of(pat))
        expect(personalAccessTokenService.expire(pat.uuid)).andReturn(pat)
        replayAll()
        personalAccessTokenServiceFacade.expireByUserUuid(user.uuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().token).isEqualTo(null)
            assertThat(it.response().userUuid).isEqualTo(user.uuid)
        }
    }
}