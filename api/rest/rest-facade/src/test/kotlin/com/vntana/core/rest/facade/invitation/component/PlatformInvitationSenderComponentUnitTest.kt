package com.vntana.core.rest.facade.invitation.component

import com.vntana.core.domain.template.email.TemplateEmail
import com.vntana.core.domain.template.email.TemplateEmailType
import com.vntana.core.notification.EmailSenderService
import com.vntana.core.notification.payload.invitation.PlatformInvitationEmailSendPayload
import com.vntana.core.rest.facade.invitation.component.impl.PlatformInvitationSenderComponentImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.template.email.TemplateEmailService
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.easymock.Mock
import org.junit.Before
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 11:32 AM
 */
class PlatformInvitationSenderComponentUnitTest : AbstractFacadeUnitTest() {

    private lateinit var senderComponent: PlatformInvitationSenderComponent

    @Mock
    private lateinit var emailSenderService: EmailSenderService

    @Mock
    private lateinit var templateEmailService: TemplateEmailService

    private val platformInvitationUrlPrefix = uuid()

    private val senderEmail = uuid()

    @Before
    fun prepare() {
        senderComponent = PlatformInvitationSenderComponentImpl(
                emailSenderService,
                templateEmailService,
                platformInvitationUrlPrefix,
                senderEmail
        )
    }

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { senderComponent.sendInvitation(null, uuid()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { senderComponent.sendInvitation("", uuid()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { senderComponent.sendInvitation(uuid(), null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { senderComponent.sendInvitation(uuid(), "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val email = uuid()
        val token = uuid()
        val emailTemplateName = uuid()
        resetAll()
        expect(templateEmailService.getByType(TemplateEmailType.PLATFORM_INVITATION)).andReturn(TemplateEmail(TemplateEmailType.PLATFORM_INVITATION, emailTemplateName))
        expect(emailSenderService.sendEmail(isA(PlatformInvitationEmailSendPayload::class.java))).andVoid()
        replayAll()
        senderComponent.sendInvitation(email, token)
        verifyAll()
    }

}