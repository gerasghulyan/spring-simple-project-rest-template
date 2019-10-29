package com.vntana.core.notification.payload;

import java.util.List;
import java.util.Map;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 5:45 PM
 */
public interface EmailSendPayload {

    List<String> recipientsEmails();

    String senderEmail();

    String subject();

    Map<String, String> properties();
}
