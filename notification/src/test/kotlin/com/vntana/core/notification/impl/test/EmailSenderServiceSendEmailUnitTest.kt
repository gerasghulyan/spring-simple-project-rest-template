package com.vntana.core.notification.impl.test

import com.sflpro.notifier.api.model.email.request.CreateEmailNotificationRequest
import com.vntana.core.notification.impl.AbstractEmailSenderServiceUnitTest
import com.vntana.core.notification.payload.TemplateEmailSendPayload
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 10/29/19
 * Time: 10:59 AM
 */
class EmailSenderServiceSendEmailUnitTest : AbstractEmailSenderServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { emailSenderService.sendEmail(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test`() {
        val payload = buildTemplateEmailSendPayload()
        val request11 = CreateEmailNotificationRequest()
        request11.subject = payload.subject()
        request11.senderEmail = payload.senderEmail()
        request11.recipientEmail = payload.recipientsEmails()[0]
        request11.properties = payload.properties()
        request11.templateName = payload.templateName()
        val request12 = CreateEmailNotificationRequest()
        request12.subject = payload.subject()
        request12.senderEmail = payload.senderEmail()
        request12.recipientEmail = payload.recipientsEmails()[1]
        request12.properties = payload.properties()
        request12.templateName = payload.templateName()
        resetAll()
        expect(executor.execute(isA(Runnable::class.java))).andAnswer { (getCurrentArguments()[0] as Runnable).run() }
        expect(emailNotificationResourceClient.createEmailNotification(request11)).andReturn(null)
        expect(executor.execute(isA(Runnable::class.java))).andAnswer { (getCurrentArguments()[0] as Runnable).run() }
        expect(emailNotificationResourceClient.createEmailNotification(request12)).andReturn(null)
        replayAll()
        emailSenderService.sendEmail(payload).let {

        }
        verifyAll()
    }

    private fun buildTemplateEmailSendPayload(templateName: String? = uuid(),
                                              recipientsEmails: List<String> = listOf(uuid(), uuid()),
                                              senderEmail: String? = uuid(),
                                              subject: String? = uuid(),
                                              properties: Map<String, String> = mapOf("a" to "aa")): TemplateEmailSendPayload {
        return object : TemplateEmailSendPayload {
            override fun templateName(): String? {
                return templateName
            }

            override fun recipientsEmails(): List<String>? {
                return recipientsEmails
            }

            override fun senderEmail(): String? {
                return senderEmail
            }

            override fun subject(): String? {
                return subject
            }

            override fun properties(): Map<String, String>? {
                return properties
            }
        }
    }

}