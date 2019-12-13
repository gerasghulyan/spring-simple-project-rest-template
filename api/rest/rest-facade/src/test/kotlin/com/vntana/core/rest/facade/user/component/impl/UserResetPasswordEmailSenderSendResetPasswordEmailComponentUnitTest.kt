package com.vntana.core.rest.facade.user.component.impl

import com.vntana.core.domain.template.email.TemplateEmail
import com.vntana.core.domain.template.email.TemplateEmailType
import com.vntana.core.notification.payload.reset.password.ResetPasswordEmailSendPayload
import com.vntana.core.rest.facade.user.component.AbstractUserResetPasswordEmailSenderComponentUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 3:09 PM
 */
class UserResetPasswordEmailSenderSendResetPasswordEmailComponentUnitTest : AbstractUserResetPasswordEmailSenderComponentUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { resetPasswordEmailSenderComponent.sendResetPasswordEmail(null, uuid()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { resetPasswordEmailSenderComponent.sendResetPasswordEmail("", uuid()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { resetPasswordEmailSenderComponent.sendResetPasswordEmail(uuid(), null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { resetPasswordEmailSenderComponent.sendResetPasswordEmail(uuid(), "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when user already verified`() {
        val templateName = uuid()
        val templateEmail = TemplateEmail(TemplateEmailType.FORGOT_PASSWORD, templateName)
        val email = uuid()
        val token = uuid()
        resetAll()
        expect(templateEmailService.getByType(TemplateEmailType.FORGOT_PASSWORD)).andReturn(templateEmail)
        expect(emailSenderService.sendEmail(isA(ResetPasswordEmailSendPayload::class.java))).andVoid()
        replayAll()
        resetPasswordEmailSenderComponent.sendResetPasswordEmail(email, token)
        verifyAll()
    }
}