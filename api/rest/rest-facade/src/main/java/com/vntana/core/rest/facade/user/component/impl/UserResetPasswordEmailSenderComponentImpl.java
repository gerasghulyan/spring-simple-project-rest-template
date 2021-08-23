package com.vntana.core.rest.facade.user.component.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.template.email.TemplateEmail;
import com.vntana.core.domain.template.email.TemplateEmailType;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.notification.EmailSenderService;
import com.vntana.core.notification.payload.reset.password.ResetPasswordEmailSendPayload;
import com.vntana.core.rest.facade.common.component.UserEmailSenderComponentPreconditionChecker;
import com.vntana.core.rest.facade.user.component.UserResetPasswordEmailSenderComponent;
import com.vntana.core.service.template.email.TemplateEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.springframework.util.Assert.hasText;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 2:13 PM
 */
@Component
public class UserResetPasswordEmailSenderComponentImpl implements UserResetPasswordEmailSenderComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResetPasswordEmailSenderComponentImpl.class);

    private final EmailSenderService emailSenderService;
    private final TemplateEmailService templateEmailService;
    private final String resetPasswordUrlPrefix;
    private final String senderEmail;
    private final UserEmailSenderComponentPreconditionChecker userEmailSenderComponentPreconditionChecker;

    public UserResetPasswordEmailSenderComponentImpl(
            final EmailSenderService emailSenderService,
            final TemplateEmailService templateEmailService,
            @Value("${reset.password.website.url}") final String resetPasswordUrlPrefix,
            @Value("${reset.password.email.send.from}") final String senderEmail,
            final UserEmailSenderComponentPreconditionChecker userEmailSenderComponentPreconditionChecker) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.emailSenderService = emailSenderService;
        this.templateEmailService = templateEmailService;
        this.resetPasswordUrlPrefix = resetPasswordUrlPrefix;
        this.senderEmail = senderEmail;
        this.userEmailSenderComponentPreconditionChecker = userEmailSenderComponentPreconditionChecker;
    }

    @Override
    public void sendResetPasswordEmail(final String email, final String token) {
        hasText(email, "The email should not be null or empty");
        hasText(token, "The token should not be null or empty");
        final SingleErrorWithStatus<UserErrorResponseModel> errors = userEmailSenderComponentPreconditionChecker.checkUser(email);
        if (errors.isPresent()) {
            return;
        }
        final TemplateEmail templateEmail = templateEmailService.getByType(TemplateEmailType.FORGOT_PASSWORD);
        final ResetPasswordEmailSendPayload payload = new ResetPasswordEmailSendPayload(
                templateEmail.getTemplateName(),
                email,
                senderEmail,
                "Reset your password",
                String.format("%s/%s", resetPasswordUrlPrefix, token)
        );
        emailSenderService.sendEmail(payload);
    }
}
