package com.vntana.core.notification;

import com.vntana.core.notification.payload.TemplateEmailSendPayload;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 5:45 PM
 */
public interface EmailSenderService {

    <T extends TemplateEmailSendPayload> void sendEmail(final T payload);
}
