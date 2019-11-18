package com.vntana.core.rest.facade.user.component.impl

import com.vntana.core.domain.template.email.TemplateEmail
import com.vntana.core.domain.template.email.TemplateEmailType
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.notification.payload.verification.VerificationEmailSendPayload
import com.vntana.core.rest.facade.user.component.AbstractUserVerificationSenderComponentUnitTest
import org.assertj.core.api.Assertions
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 11/12/19
 * Time: 3:22 PM
 */
class UserVerificationSenderComponentSendVerificationEmailUnitTest : AbstractUserVerificationSenderComponentUnitTest() {

    @Test
    fun `test when user not found`() {
        val request = restHelper.buildSendUserVerificationRequest()
        resetAll()
        expect(userService.findByUuid(request.uuid)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(userVerificationSenderComponent.sendVerificationEmail(request), UserErrorResponseModel.NOT_FOUND_FOR_UUID)
        verifyAll()
    }

    @Test
    fun `test when user already verified`() {
        val user = userHelper.buildUser()
        user.verified = true
        val request = restHelper.buildSendUserVerificationRequest(uuid = user.uuid)
        resetAll()
        expect(userService.findByUuid(request.uuid)).andReturn(Optional.of(user))
        replayAll()
        userVerificationSenderComponent.sendVerificationEmail(request).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.USER_ALREADY_VERIFIED)
        }
        verifyAll()
    }

    @Test
    fun `test`() {
        val user = userHelper.buildUser()
        val templateName = uuid()
        val templateEmail = TemplateEmail(TemplateEmailType.USER_VERIFICATION, templateName)
        val request = restHelper.buildSendUserVerificationRequest(uuid = user.uuid)
        resetAll()
        expect(userService.findByUuid(request.uuid)).andReturn(Optional.of(user))
        expect(templateEmailService.getByType(TemplateEmailType.USER_VERIFICATION)).andReturn(templateEmail)
        expect(emailSenderService.sendEmail(isA(VerificationEmailSendPayload::class.java))).andVoid()
        replayAll()
        userVerificationSenderComponent.sendVerificationEmail(request).let {
            assertBasicSuccessResultResponse(it)
            Assertions.assertThat(it.response().uuid).isEqualTo(user.uuid)
        }
        verifyAll()
    }
}