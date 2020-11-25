package com.vntana.core.rest.facade.invitation.user.component.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.template.email.TemplateEmail;
import com.vntana.core.domain.template.email.TemplateEmailType;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.invitation.user.request.SendInvitationForClientUserRequest;
import com.vntana.core.model.invitation.user.request.SendInvitationForOrganizationUserRequest;
import com.vntana.core.model.invitation.user.response.SendInvitationUserResultResponse;
import com.vntana.core.model.invitation.user.response.model.SendInvitationUserResponseModel;
import com.vntana.core.notification.EmailSenderService;
import com.vntana.core.notification.payload.invitation.user.InvitationUserToClientEmailSendPayload;
import com.vntana.core.notification.payload.invitation.user.InvitationUserToOrganizationEmailSendPayload;
import com.vntana.core.rest.facade.invitation.user.component.InvitationUserSenderComponent;
import com.vntana.core.service.invitation.user.InvitationUserToClientService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.template.email.TemplateEmailService;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private final OrganizationService organizationService;
    private final InvitationUserToClientService invitationUserToClientService;
    private final String websiteUrl;
    private final String senderEmail;
    private final String emailSubject;
    private final String isClientInvitation;

    public InvitationUserSenderComponentImpl(
            final EmailSenderService emailSenderService,
            final TemplateEmailService templateEmailService,
            final UserService userService,
            final OrganizationService organizationService,
            final InvitationUserToClientService invitationUserToClientService,
            @Value("${user.invitation.website.url}") final String websiteUrl,
            @Value("${user.invitation.email.send.from}") final String senderEmail,
            @Value("${user.invitation.email.subject}") final String emailSubject,
            @Value("isClientInvitation=true") final String isClientInvitation) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.emailSenderService = emailSenderService;
        this.templateEmailService = templateEmailService;
        this.userService = userService;
        this.organizationService = organizationService;
        this.invitationUserToClientService = invitationUserToClientService;
        this.websiteUrl = websiteUrl;
        this.senderEmail = senderEmail;
        this.emailSubject = emailSubject;
        this.isClientInvitation = isClientInvitation;
    }

    @Override
    public SendInvitationUserResultResponse sendInvitationForOrganization(final SendInvitationForOrganizationUserRequest request) {
        LOGGER.debug("Sending user invitation for organization for request - {}", request);
        final TemplateEmail templateEmail = templateEmailService.getByType(TemplateEmailType.USER_INVITATION);
        final User user = userService.getByUuid(request.getInviterUserUuid());
        final Organization organization = organizationService.getByUuid(request.getOrganizationUuid());
        final InvitationUserToOrganizationEmailSendPayload payload = new InvitationUserToOrganizationEmailSendPayload(
                templateEmail.getTemplateName(),
                request.getEmail(),
                senderEmail,
                emailSubject,
                String.format("%s/%s", websiteUrl, request.getToken()),
                user.getFullName(),
                organization.getName()
        );
        emailSenderService.sendEmail(payload);
        LOGGER.debug("Successfully sent user invitation for request - {}", request);
        return new SendInvitationUserResultResponse(new SendInvitationUserResponseModel());
    }

    @Transactional(readOnly = true)
    @Override
    public SendInvitationUserResultResponse sendInvitationForClients(final SendInvitationForClientUserRequest request) {
        LOGGER.debug("Sending user invitation for clients for request - {}", request);
        final TemplateEmail templateEmail = templateEmailService.getByType(TemplateEmailType.USER_INVITATION);
        final User user = userService.getByUuid(request.getInviterUserUuid());
        request.getInvitationTokens().forEach((key, value) -> {
            final InvitationUserToClientEmailSendPayload payload = new InvitationUserToClientEmailSendPayload(
                    templateEmail.getTemplateName(),
                    request.getEmail(),
                    senderEmail,
                    emailSubject,
                    String.format("%s/%s?%s", websiteUrl, value, isClientInvitation),
                    user.getFullName(),
                    invitationUserToClientService.getByUuid(key).getClientOrganization().getName()
            );
            emailSenderService.sendEmail(payload);
        });
        return new SendInvitationUserResultResponse(new SendInvitationUserResponseModel());
    }
}