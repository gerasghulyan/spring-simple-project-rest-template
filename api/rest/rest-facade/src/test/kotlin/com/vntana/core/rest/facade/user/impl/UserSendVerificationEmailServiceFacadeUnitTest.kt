package com.vntana.core.rest.facade.user.impl

import com.vntana.core.model.user.response.SendUserVerificationResponse
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/12/19
 * Time: 2:13 PM
 */
class UserSendVerificationEmailServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test`() {
        val request = restHelper.buildSendUserVerificationRequest()
        resetAll()
        expect(userVerificationSenderComponent.sendVerificationEmail(request)).andReturn(SendUserVerificationResponse(uuid()))
        replayAll()
        assertBasicSuccessResultResponse(userServiceFacade.sendVerificationEmail(request))
        verifyAll()
    }
}