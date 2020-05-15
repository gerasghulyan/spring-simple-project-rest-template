package com.vntana.core.rest.facade.invitation.user.component.impl;

import com.vntana.core.domain.template.email.TemplateEmail;
import com.vntana.core.domain.template.email.TemplateEmailType;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.invitation.user.request.SendInvitationUserRequest;
import com.vntana.core.model.invitation.user.response.SendInvitationUserResultResponse;
import com.vntana.core.model.invitation.user.response.model.SendInvitationUserResponseModel;
import com.vntana.core.notification.EmailSenderService;
import com.vntana.core.notification.payload.invitation.user.InvitationUserEmailSendPayload;
import com.vntana.core.rest.facade.invitation.user.component.InvitationUserSenderComponent;
import com.vntana.core.service.template.email.TemplateEmailService;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 11:12 AM
 */
@Component
public class InvitationUserSenderComponentImpl implements InvitationUserSenderComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationUserSenderComponentImpl.class);

    private final EmailSenderService emailSenderService;
    private final TemplateEmailService templateEmailService;
    private final UserService userService;
    private final String websiteUrl;
    private final String senderEmail;
    private final String emailSubject;

    public InvitationUserSenderComponentImpl(
            final EmailSenderService emailSenderService,
            final TemplateEmailService templateEmailService,
            final UserService userService,
            @Value("${user.invitation.website.url}") final String websiteUrl,
            @Value("${user.invitation.email.send.from}") final String senderEmail,
            @Value("${user.invitation.email.subject}") final String emailSubject) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.emailSenderService = emailSenderService;
        this.templateEmailService = templateEmailService;
        this.userService = userService;
        this.websiteUrl = websiteUrl;
        this.senderEmail = senderEmail;
        this.emailSubject = emailSubject;
    }

    @Override
    public SendInvitationUserResultResponse sendInvitation(final SendInvitationUserRequest request) {
        LOGGER.debug("Sending user invitation for request - {}", request);
        final TemplateEmail templateEmail = templateEmailService.getByType(TemplateEmailType.USER_INVITATION);
        final User user = userService.getByUuid(request.getInviterUserUuid());
        final InvitationUserEmailSendPayload payload = new InvitationUserEmailSendPayload(
                templateEmail.getTemplateName(),
                request.getEmail(),
                senderEmail,
                emailSubject,
                String.format("%s/%s", websiteUrl, request.getToken()),
                user.getFullName(),
                request.getOrganizationName()
        );
        emailSenderService.sendEmail(payload);
        LOGGER.debug("Successfully sent user invitation for request - {}", request);
        return new SendInvitationUserResultResponse(new SendInvitationUserResponseModel());
    }
}