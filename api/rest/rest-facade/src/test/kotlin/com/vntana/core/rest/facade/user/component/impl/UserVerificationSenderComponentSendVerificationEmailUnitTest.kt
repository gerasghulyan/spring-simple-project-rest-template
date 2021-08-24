package com.vntana.core.rest.facade.user.component.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.template.email.TemplateEmail
import com.vntana.core.domain.template.email.TemplateEmailType
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.notification.payload.verification.VerificationEmailSendPayload
import com.vntana.core.rest.facade.user.component.AbstractUserVerificationSenderComponentUnitTest
import org.apache.http.HttpStatus
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
        expect(userEmailSenderComponentPreconditionChecker.checkUser(request.email)).andReturn(SingleErrorWithStatus.of(
            HttpStatus.SC_UNPROCESSABLE_ENTITY, UserErrorResponseModel.NOT_FOUND_FOR_UUID))
        replayAll()
        assertBasicErrorResultResponse(
            userVerificationSenderComponent.sendVerificationEmail(request),
            UserErrorResponseModel.NOT_FOUND_FOR_UUID
        )
        verifyAll()
    }

    @Test
    fun `test when user is external`() {
        val request = restHelper.buildSendUserVerificationRequest()
        resetAll()
        expect(userEmailSenderComponentPreconditionChecker.checkUser(request.email)).andReturn(SingleErrorWithStatus.of(
            HttpStatus.SC_UNPROCESSABLE_ENTITY, UserErrorResponseModel.INVALID_USER))
        replayAll()
        assertBasicErrorResultResponse(
            userVerificationSenderComponent.sendVerificationEmail(request),
            UserErrorResponseModel.INVALID_USER
        )
        verifyAll()
    }

    @Test
    fun `test when user already verified`() {
        val user = userHelper.buildUserWithOrganizationOwnerRole()
        user.verified = true
        val request = restHelper.buildSendUserVerificationRequest(email = user.email)
        resetAll()
        expect(userEmailSenderComponentPreconditionChecker.checkUser(request.email)).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByEmail(request.email)).andReturn(user)
        replayAll()
        userVerificationSenderComponent.sendVerificationEmail(request).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.USER_ALREADY_VERIFIED)
        }
        verifyAll()
    }

    @Test
    fun `test sendVerificationEmail`() {
        val user = userHelper.buildUserWithOrganizationOwnerRole()
        val templateName = uuid()
        val templateEmail = TemplateEmail(TemplateEmailType.USER_VERIFICATION, templateName)
        val request = restHelper.buildSendUserVerificationRequest(email = user.email)
        resetAll()
        expect(userEmailSenderComponentPreconditionChecker.checkUser(request.email)).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByEmail(request.email)).andReturn(user)
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