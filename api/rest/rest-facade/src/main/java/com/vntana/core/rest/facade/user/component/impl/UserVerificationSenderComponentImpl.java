package com.vntana.core.rest.facade.user.component.impl;

import com.vntana.core.domain.template.email.TemplateEmail;
import com.vntana.core.domain.template.email.TemplateEmailType;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.SendUserVerificationRequest;
import com.vntana.core.model.user.response.SendUserVerificationResponse;
import com.vntana.core.notification.EmailSenderService;
import com.vntana.core.notification.payload.verification.VerificationEmailSendPayload;
import com.vntana.core.rest.facade.user.component.UserVerificationSenderComponent;
import com.vntana.core.service.template.email.TemplateEmailService;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/12/19
 * Time: 3:09 PM
 */
@Component
public class UserVerificationSenderComponentImpl implements UserVerificationSenderComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserVerificationSenderComponentImpl.class);

    private final UserService userService;
    private final EmailSenderService emailSenderService;
    private final TemplateEmailService templateEmailService;
    private final String verificationUrlPrefix;
    private final String senderEmail;

    public UserVerificationSenderComponentImpl(final UserService userService,
                                               final EmailSenderService emailSenderService,
                                               final TemplateEmailService templateEmailService,
                                               @Value("${verification.website.url}") final String verificationUrlPrefix,
                                               @Value("${verification.email.send.from}") final String senderEmail) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.emailSenderService = emailSenderService;
        this.templateEmailService = templateEmailService;
        this.verificationUrlPrefix = verificationUrlPrefix;
        this.senderEmail = senderEmail;
    }

    @Override
    public SendUserVerificationResponse sendVerificationEmail(final SendUserVerificationRequest request) {
        final Optional<User> userOptional = userService.findByUuid(request.getUuid());
        if (!userOptional.isPresent()) {
            return new SendUserVerificationResponse(Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_UUID));
        }
        final User user = userOptional.get();
        if (user.getVerified()) {
            return new SendUserVerificationResponse(Collections.singletonList(UserErrorResponseModel.USER_ALREADY_VERIFIED));
        }
        final TemplateEmail templateEmail = templateEmailService.getByType(TemplateEmailType.USER_VERIFICATION);
        final VerificationEmailSendPayload payload = new VerificationEmailSendPayload(
                templateEmail.getTemplateName(),
                user.getEmail(),
                senderEmail,
                "Confirm your e-mail address",
                String.format("%s/%s", verificationUrlPrefix, request.getToken())
        );
        emailSenderService.sendEmail(payload);
        return new SendUserVerificationResponse(request.getUuid());
    }
}
