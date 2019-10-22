package com.vntana.core.rest.facade.token.listener.impl;

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient;
import com.sflpro.notifier.api.model.common.result.ResultResponseModel;
import com.sflpro.notifier.api.model.email.request.CreateEmailNotificationRequest;
import com.sflpro.notifier.api.model.email.response.CreateEmailNotificationResponse;
import com.vntana.core.domain.token.ResetPasswordToken;
import com.vntana.core.rest.facade.token.event.ResetPasswordTokenCreatedEvent;
import com.vntana.core.rest.facade.token.listener.ResetPasswordTokenCreatedEventListener;
import com.vntana.core.service.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

/**
 * Created by Arthur Asatryan.
 * Date: 10/22/19
 * Time: 12:39 PM
 */
@Component
@Lazy(false)
public class ResetPasswordTokenCreatedEventListenerImpl implements ResetPasswordTokenCreatedEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordTokenCreatedEventListenerImpl.class);

    private final TokenService tokenService;

    private final EmailNotificationResourceClient emailNotificationResourceClient;

    public ResetPasswordTokenCreatedEventListenerImpl(final TokenService tokenService, final EmailNotificationResourceClient emailNotificationResourceClient) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.tokenService = tokenService;
        this.emailNotificationResourceClient = emailNotificationResourceClient;
    }

    @EventListener
    @Override
    public void listen(final ResetPasswordTokenCreatedEvent event) {
        final ResetPasswordToken token = tokenService.findByUuid(event.getUuid())
                .filter(ResetPasswordToken.class::isInstance)
                .map(ResetPasswordToken.class::cast)
                .orElseThrow(() -> {
                    LOGGER.error("Can not find reset password token for uuid - {}", event.getUuid());
                    return new IllegalStateException(format("Can not find reset password token for uuid - %s", event.getUuid()));
                });
        final CreateEmailNotificationRequest emailRequest = new CreateEmailNotificationRequest();
        emailRequest.setRecipientEmail(token.getUser().getEmail());
        emailRequest.setSubject("Reset password");
        emailRequest.setBody(format("Reset password token - %s", token.getToken()));
        final ResultResponseModel<CreateEmailNotificationResponse> emailNotification = emailNotificationResourceClient.createEmailNotification(emailRequest);
        if (emailNotification.hasErrors()) {
            LOGGER.error("Can not send reset password email notification with request - {}", emailRequest);
            throw new IllegalStateException(format("Can not send reset password email notification with request - %s", emailRequest));
        }
    }
}
