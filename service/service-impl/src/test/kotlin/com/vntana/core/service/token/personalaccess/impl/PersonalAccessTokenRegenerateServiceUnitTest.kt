package com.vntana.core.service.token.personalaccess.impl

import com.vntana.core.service.token.personalaccess.AbstractPersonalAccessTokenServiceUnitTest
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 10:30 AM
 */
class PersonalAccessTokenRegenerateServiceUnitTest : AbstractPersonalAccessTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        Assertions.assertThatThrownBy { personalAccessTokenService.regenerateTokenForUser(null) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }
}