package com.vntana.core.rest.facade.invitation.organization.component

import com.vntana.core.domain.template.email.TemplateEmail
import com.vntana.core.domain.template.email.TemplateEmailType
import com.vntana.core.helper.invitation.organization.InvitationOrganizationRestTestHelper
import com.vntana.core.notification.EmailSenderService
import com.vntana.core.notification.payload.invitation.organization.InvitationOrganizationEmailSendPayload
import com.vntana.core.rest.facade.invitation.organization.component.impl.InvitationOrganizationSenderComponentImpl
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.template.email.TemplateEmailService
import org.easymock.EasyMock
import org.easymock.Mock
import org.junit.Before
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 5:45 PM
 */
class InvitationOrganizationSenderComponentUnitTest : AbstractServiceFacadeUnitTest() {

    private lateinit var senderComponent: InvitationOrganizationSenderComponent

    @Mock
    private lateinit var emailSenderService: EmailSenderService

    @Mock
    private lateinit var templateEmailService: TemplateEmailService

    private val verificationUrlPrefix: String = uuid()

    private val senderEmail: String = uuid()

    private val emailSubject: String = uuid()

    private val restTestHelper = InvitationOrganizationRestTestHelper()

    @Before
    fun prepare() {
        senderComponent = InvitationOrganizationSenderComponentImpl(
                emailSenderService,
                templateEmailService,
                verificationUrlPrefix,
                senderEmail,
                emailSubject
        )
    }

    @Test
    fun `test sendVerificationEmail`() {
        val request = restTestHelper.buildSendInvitationOrganizationRequest()
        val templateName = uuid()
        val templateEmail = TemplateEmail(TemplateEmailType.USER_VERIFICATION, templateName)
        resetAll()
        EasyMock.expect(templateEmailService.getByType(TemplateEmailType.ORGANIZATION_INVITATION)).andReturn(templateEmail)
        EasyMock.expect(emailSenderService.sendEmail(EasyMock.isA(InvitationOrganizationEmailSendPayload::class.java))).andVoid()
        replayAll()
        assertBasicSuccessResultResponse(senderComponent.sendInvitation(request))
        verifyAll()
    }
}