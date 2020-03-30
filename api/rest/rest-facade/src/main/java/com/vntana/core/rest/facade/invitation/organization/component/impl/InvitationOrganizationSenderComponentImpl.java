package com.vntana.core.rest.facade.invitation.organization.component.impl;

import com.vntana.core.domain.template.email.TemplateEmail;
import com.vntana.core.domain.template.email.TemplateEmailType;
import com.vntana.core.model.invitation.organization.request.SendInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.response.SendInvitationOrganizationResponse;
import com.vntana.core.model.invitation.organization.response.model.SendInvitationOrganizationResponseModel;
import com.vntana.core.notification.EmailSenderService;
import com.vntana.core.notification.payload.invitation.organization.InvitationOrganizationEmailSendPayload;
import com.vntana.core.rest.facade.invitation.organization.component.InvitationOrganizationSenderComponent;
import com.vntana.core.service.template.email.TemplateEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 5:25 PM
 */
@Component
public class InvitationOrganizationSenderComponentImpl implements InvitationOrganizationSenderComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationOrganizationSenderComponentImpl.class);

    private final EmailSenderService emailSenderService;
    private final TemplateEmailService templateEmailService;
    private final String websiteUrl;
    private final String senderEmail;
    private final String emailSubject;

    public InvitationOrganizationSenderComponentImpl(
            final EmailSenderService emailSenderService,
            final TemplateEmailService templateEmailService,
            @Value("${organization.invitation.website.url}") final String websiteUrl,
            @Value("${organization.invitation.email.send.from}") final String senderEmail,
            @Value("${organization.invitation.email.subject}") final String emailSubject) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.emailSenderService = emailSenderService;
        this.templateEmailService = templateEmailService;
        this.websiteUrl = websiteUrl;
        this.senderEmail = senderEmail;
        this.emailSubject = emailSubject;
    }

    @Override
    public SendInvitationOrganizationResponse sendInvitation(final SendInvitationOrganizationRequest request) {
        final TemplateEmail templateEmail = templateEmailService.getByType(TemplateEmailType.ORGANIZATION_INVITATION);
        final InvitationOrganizationEmailSendPayload payload = new InvitationOrganizationEmailSendPayload(
                templateEmail.getTemplateName(),
                request.getEmail(),
                senderEmail,
                emailSubject,
                String.format("%s/%s", websiteUrl, request.getToken())
        );
        emailSenderService.sendEmail(payload);
        return new SendInvitationOrganizationResponse(new SendInvitationOrganizationResponseModel());
    }
}
