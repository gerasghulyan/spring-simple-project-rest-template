package com.vntana.core.notification.payload;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 5:46 PM
 */
public interface TemplateEmailSendPayload extends EmailSendPayload {

    String templateName();
}
