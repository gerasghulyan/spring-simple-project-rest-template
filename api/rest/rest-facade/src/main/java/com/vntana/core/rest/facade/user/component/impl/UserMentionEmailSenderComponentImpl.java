package com.vntana.core.rest.facade.user.component.impl;

import com.vntana.core.domain.template.email.TemplateEmail;
import com.vntana.core.domain.template.email.TemplateEmailType;
import com.vntana.core.notification.EmailSenderService;
import com.vntana.core.notification.payload.mention.user.MentionUserEmailSendPayload;
import com.vntana.core.rest.facade.user.component.UserMentionEmailSenderComponent;
import com.vntana.core.model.user.request.SendUserMentionRequest;
import com.vntana.core.service.template.email.TemplateEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 06.10.2020
 * Time: 16:45
 */
@Component
public class UserMentionEmailSenderComponentImpl implements UserMentionEmailSenderComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMentionEmailSenderComponentImpl.class);
    private static final String EMAIL_SUBJECT = "You are mentioned";

    private final EmailSenderService emailSenderService;
    private final TemplateEmailService templateEmailService;
    private final String userMentionedUrlPrefix;
    private final String senderEmail;

    public UserMentionEmailSenderComponentImpl(final EmailSenderService emailSenderService,
                                               final TemplateEmailService templateEmailService,
                                               @Value("${user.mention.website.url}") final String userMentionedUrlPrefix,
                                               @Value("${user.mention.email.send.from}") final String senderEmail
    ) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.emailSenderService = emailSenderService;
        this.templateEmailService = templateEmailService;
        this.userMentionedUrlPrefix = userMentionedUrlPrefix;
        this.senderEmail = senderEmail;
    }

    @Override
    public void sendMentionedUsersEmails(final SendUserMentionRequest dto) {
        LOGGER.debug("Sending email to mentioned users for dto - {}", dto);
        Assert.notNull(dto, "The SendUserMentionDto should not be null");
        final MentionUserEmailSendPayload payload = createMentionUserEmailSendPayload(dto);
        emailSenderService.sendEmail(payload);
        LOGGER.debug("Successfully sent email to mentioned users for dto - {}", dto);
    }

    private MentionUserEmailSendPayload createMentionUserEmailSendPayload(final SendUserMentionRequest dto) {
        final TemplateEmail templateEmail = templateEmailService.getByType(TemplateEmailType.USER_MENTION);
        return new MentionUserEmailSendPayload(
                templateEmail.getTemplateName(),
                dto.getEmail(),
                senderEmail,
                EMAIL_SUBJECT,
                dto.getPromptingUserName(),
                dto.getMentionedUserName(),
                dto.getEntityType().name(),
                dto.getProductName(),
                String.format("%s/%s/%s/products/edit/%s?type=%s&entityUuid=%s",
                        userMentionedUrlPrefix, dto.getOrganizationSlug(), dto.getClientSlug(), dto.getProductUuid(), dto.getEntityType(), dto.getEntityUuid())
        );
    }
}
