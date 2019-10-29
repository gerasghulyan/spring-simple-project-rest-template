package com.vntana.core.notification.impl;

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient;
import com.sflpro.notifier.api.model.email.request.CreateEmailNotificationRequest;
import com.vntana.core.notification.EmailSenderService;
import com.vntana.core.notification.payload.TemplateEmailSendPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.Executor;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 5:50 PM
 */

@Component
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    private final Executor executor;

    private final EmailNotificationResourceClient emailNotificationResourceClient;

    public EmailSenderServiceImpl(@Qualifier("notificationSenderExecutor") final Executor executor, final EmailNotificationResourceClient emailNotificationResourceClient) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.executor = executor;
        this.emailNotificationResourceClient = emailNotificationResourceClient;
    }

    @Override
    public <T extends TemplateEmailSendPayload> void sendEmail(final T payload) {
        LOGGER.debug("Processing sendEmail service method for payload - {}", payload);
        Assert.notNull(payload, "The template email send payload should not be null");
        payload.recipientsEmails().forEach(recipientEmail -> {
            final CreateEmailNotificationRequest request = buildCreateEmailNotificationRequest(payload);
            request.setRecipientEmail(recipientEmail);
            executor.execute(() -> emailNotificationResourceClient.createEmailNotification(request));
        });
        LOGGER.debug("Successfully processed sendEmail service method for payload - {}", payload);
    }

    private <T extends TemplateEmailSendPayload> CreateEmailNotificationRequest buildCreateEmailNotificationRequest(final T payload) {
        final CreateEmailNotificationRequest request = new CreateEmailNotificationRequest();
        request.setTemplateName(payload.templateName());
        request.setSenderEmail(payload.senderEmail());
        request.setSubject(payload.subject());
        request.setProperties(payload.properties());
        return request;
    }
}
