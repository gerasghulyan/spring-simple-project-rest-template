package com.vntana.core.notification.payload.invitation.organization;

import com.vntana.core.notification.payload.AbstractTemplateEmailSendPayload;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 5:50 PM
 */
public class InvitationOrganizationEmailSendPayload extends AbstractTemplateEmailSendPayload {

    public static final String PROPERTIES_LINK = "link";
    public static final String PROPERTIES_ORGANIZATION_NAME = "organizationName";

    private final Map<String, String> propertiesMap;

    public InvitationOrganizationEmailSendPayload(final String templateName,
                                                  final String recipientEmail,
                                                  final String senderEmail,
                                                  final String subject,
                                                  final String verificationLinkAddress,
                                                  final String organizationName) {

        super(templateName, recipientEmail, senderEmail, subject);
        propertiesMap = new HashMap<>();
        propertiesMap.put(PROPERTIES_LINK, verificationLinkAddress);
        propertiesMap.put(PROPERTIES_ORGANIZATION_NAME, organizationName);
    }

    @Override
    public Map<String, String> properties() {
        return propertiesMap;
    }
}