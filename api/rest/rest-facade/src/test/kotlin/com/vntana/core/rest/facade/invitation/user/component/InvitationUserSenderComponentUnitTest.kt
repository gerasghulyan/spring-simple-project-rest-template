package com.vntana.core.rest.facade.invitation.user.component

import com.vntana.core.domain.template.email.TemplateEmail
import com.vntana.core.domain.template.email.TemplateEmailType
import com.vntana.core.helper.invitation.user.InvitationUserRestTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.notification.EmailSenderService
import com.vntana.core.notification.payload.invitation.user.InvitationUserToOrganizationEmailSendPayload
import com.vntana.core.rest.facade.invitation.user.component.impl.InvitationUserSenderComponentImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.template.email.TemplateEmailService
import com.vntana.core.service.user.UserService
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 11:26 AM
 */
class InvitationUserSenderComponentUnitTest : AbstractFacadeUnitTest() {

    private lateinit var senderComponent: InvitationUserSenderComponent

    @Mock
    private lateinit var emailSenderService: EmailSenderService

    @Mock
    private lateinit var templateEmailService: TemplateEmailService
    
    @Mock
    private lateinit var userService: UserService
    
    @Mock
    private lateinit var organizationService: OrganizationService

    private val verificationUrlPrefix: String = uuid()

    private val senderEmail: String = uuid()

    private val emailSubject: String = uuid()

    private val restTestHelper = InvitationUserRestTestHelper()
    
    private val userCommonTestHelper = UserCommonTestHelper()
    
    private val organizationCommonTestHelper =  OrganizationCommonTestHelper()

    @Before
    fun prepare() {
        senderComponent = InvitationUserSenderComponentImpl(
                emailSenderService,
                templateEmailService,
                userService,
                organizationService,
                verificationUrlPrefix,
                senderEmail,
                emailSubject
        )
    }

    @Test
    fun `test sendVerificationEmail`() {
        val request = restTestHelper.buildSendInvitationUserRequest()
        val user = userCommonTestHelper.buildUser()
        val organization = organizationCommonTestHelper.buildOrganization()
        val templateName = uuid()
        val templateEmail = TemplateEmail(TemplateEmailType.USER_VERIFICATION, templateName)
        resetAll()
        expect(templateEmailService.getByType(TemplateEmailType.USER_INVITATION)).andReturn(templateEmail)
        expect(userService.getByUuid(request.inviterUserUuid)).andReturn(user)
        expect(organizationService.getByUuid(request.organizationUuid)).andReturn(organization)
        expect(emailSenderService.sendEmail(EasyMock.isA(InvitationUserToOrganizationEmailSendPayload::class.java))).andVoid()
        replayAll()
        assertBasicSuccessResultResponse(senderComponent.sendInvitationForOrganization(request))
        verifyAll()
    }
}