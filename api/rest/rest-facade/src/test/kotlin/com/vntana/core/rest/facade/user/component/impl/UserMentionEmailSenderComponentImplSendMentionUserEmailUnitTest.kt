package com.vntana.core.rest.facade.user.component.impl

import com.vntana.core.domain.template.email.TemplateEmail
import com.vntana.core.domain.template.email.TemplateEmailType
import com.vntana.core.notification.payload.mention.user.MentionUserEmailSendPayload
import com.vntana.core.rest.facade.user.component.AbstractUserMentionEmailSenderComponentImplUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 07.10.2020
 * Time: 14:57
 */
class UserMentionEmailSenderComponentImplSendMentionUserEmailUnitTest : AbstractUserMentionEmailSenderComponentImplUnitTest() {

    @Test
    fun `test with invalid argument`() {
        resetAll()
        replayAll()
        assertThatThrownBy { mentionEmailSenderComponent.sendMentionedUsersEmails(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }
    
    @Test
    fun test() {
        val templateName = uuid()
        val templateEmail = TemplateEmail(TemplateEmailType.USER_MENTION, templateName)
        val dto = buildSendUserMentionRequest()
        resetAll()
        expect(templateEmailService.getByType(TemplateEmailType.USER_MENTION)).andReturn(templateEmail)
        expect(emailSenderService.sendEmail(isA(MentionUserEmailSendPayload::class.java))).andVoid()
        replayAll()
        mentionEmailSenderComponent.sendMentionedUsersEmails(dto)
        verifyAll()
    }
}