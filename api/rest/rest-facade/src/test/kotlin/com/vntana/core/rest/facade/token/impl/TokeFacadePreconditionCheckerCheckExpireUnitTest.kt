package com.vntana.core.rest.facade.token.impl

import com.vntana.commons.api.model.response.ErrorResponseModel
import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.facade.token.AbstractTokenFacadePreconditionCheckerUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 2:09 PM
 */
class TokeFacadePreconditionCheckerCheckExpireUnitTest : AbstractTokenFacadePreconditionCheckerUnitTest() {

    @Test
    fun `test with blank uuid`() {
        resetAll()
        replayAll()
        preconditionChecker.checkExpire(null).let {
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
            Assertions.assertThat(it.error).isEqualTo(TokenErrorResponseModel.MISSING_TOKEN)
        }
        preconditionChecker.checkExpire(emptyString()).let {
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
            Assertions.assertThat(it.error).isEqualTo(TokenErrorResponseModel.MISSING_TOKEN)
        }
        verifyAll()
    }

    @Test
    fun `test token not found`() {
        resetAll()
        val token = uuid()
        expect(tokenService.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkExpire(token).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(TokenErrorResponseModel.TOKEN_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val tokenInvitationOrganization = commonTestHelper.buildTokenInvitationOrganization()
        val token = tokenInvitationOrganization.token
        expect(tokenService.findByToken(token)).andReturn(Optional.of(tokenInvitationOrganization))
        replayAll()
        assertThat(preconditionChecker.checkExpire(token)).isEqualTo(SingleErrorWithStatus.empty<ErrorResponseModel>())
        verifyAll()
    }
}