package com.vntana.core.notification.payload.invitation.user;

import com.vntana.core.notification.payload.AbstractTemplateEmailSendPayload;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Diana Gevorgyan
 * Date: 11/18/20
 * Time: 3:32 PM
 */
public class InvitationUserToClientEmailSendPayload extends AbstractTemplateEmailSendPayload {

    public static final String PROPERTIES_LINK = "link";
    public static final String PROPERTIES_ORGANIZATION_NAME = "organizationName";
    public static final String PROPERTIES_INVITER_USER_FULL_NAME = "inviterUserFullName";

    private final Map<String, String> propertiesMap;

    public InvitationUserToClientEmailSendPayload(final String templateName,
                                                        final String recipientEmail,
                                                        final String senderEmail,
                                                        final String subject,
                                                        final String verificationLinkAddress,
                                                        final String inviterUserFullName,
                                                        final String clientName) {

        super(templateName, recipientEmail, senderEmail, subject);
        propertiesMap = new HashMap<>();
        propertiesMap.put(PROPERTIES_LINK, verificationLinkAddress);
        propertiesMap.put(PROPERTIES_INVITER_USER_FULL_NAME, inviterUserFullName);
        propertiesMap.put(PROPERTIES_ORGANIZATION_NAME, clientName);
    }

    @Override
    public Map<String, String> properties() {
        return propertiesMap;
    }
}
