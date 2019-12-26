package com.vntana.core.rest.facade.invitation.component.impl;

import com.vntana.core.domain.template.email.TemplateEmail;
import com.vntana.core.domain.template.email.TemplateEmailType;
import com.vntana.core.notification.EmailSenderService;
import com.vntana.core.notification.payload.invitation.PlatformInvitationEmailSendPayload;
import com.vntana.core.rest.facade.invitation.component.PlatformInvitationSenderComponent;
import com.vntana.core.service.template.email.TemplateEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/25/19
 * Time: 5:47 PM
 */
@Component
public class PlatformInvitationSenderComponentImpl implements PlatformInvitationSenderComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformInvitationSenderComponentImpl.class);

    private final EmailSenderService emailSenderService;
    private final TemplateEmailService templateEmailService;
    private final String platformInvitationUrlPrefix;
    private final String senderEmail;

    public PlatformInvitationSenderComponentImpl(final EmailSenderService emailSenderService,
                                                 final TemplateEmailService templateEmailService,
                                                 @Value("${platform.invitation.website.url}") final String platformInvitationUrlPrefix,
                                                 @Value("${platform.invitation.email.send.from}") final String senderEmail) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.emailSenderService = emailSenderService;
        this.templateEmailService = templateEmailService;
        this.platformInvitationUrlPrefix = platformInvitationUrlPrefix;
        this.senderEmail = senderEmail;
    }

    @Override
    public void sendInvitation(final String email, final String token) {
        Assert.hasText(email, "The email should not be null or empty");
        Assert.hasText(token, "The token should not be null or empty");
        final TemplateEmail templateEmail = templateEmailService.getByType(TemplateEmailType.PLATFORM_INVITATION);
        final PlatformInvitationEmailSendPayload payload = new PlatformInvitationEmailSendPayload(
                templateEmail.getTemplateName(),
                email,
                senderEmail,
                "Platform invitation",
                String.format("%s/%s", platformInvitationUrlPrefix, token)
        );
        emailSenderService.sendEmail(payload);
    }
}
