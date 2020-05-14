package com.vntana.core.rest.facade.token.component.impl

import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.facade.token.component.AbstractTokenFacadePreconditionCheckerUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 2:07 PM
 */
class TokenFacadePreconditionCheckerCheckIsExpiredUnitTest : AbstractTokenFacadePreconditionCheckerUnitTest() {

    @Test
    fun `test with blank uuid`() {
        resetAll()
        replayAll()
        preconditionChecker.checkIsExpired(null).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
            assertThat(it.error).isEqualTo(TokenErrorResponseModel.MISSING_TOKEN)
        }
        preconditionChecker.checkIsExpired(emptyString()).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
            assertThat(it.error).isEqualTo(TokenErrorResponseModel.MISSING_TOKEN)
        }
        verifyAll()
    }
}