package com.vntana.core.notification.payload.mention.user;

import com.vntana.core.notification.payload.AbstractTemplateEmailSendPayload;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vardan Aivazian
 * Date: 06.10.2020
 * Time: 16:13
 */
public class MentionUserEmailSendPayload extends AbstractTemplateEmailSendPayload {

    private final Map<String, String> propertiesMap;

    private static final String PROPERTIES_PROMPTING_USER_NAME = "promptingUserName";
    private static final String PROPERTIES_MENTIONED_USER_NAME = "mentionedUserName";
    private static final String PROPERTIES_LOCATION = "location";
    private static final String PROPERTIES_PRODUCT_NAME = "productName";
    private static final String PROPERTIES_LINK = "hyperLink";

    public MentionUserEmailSendPayload(final String templateName,
                                       final String recipientEmail,
                                       final String senderEmail,
                                       final String subject,
                                       final String promptingUserName,
                                       final String mentionedUserName,
                                       final String type,
                                       final String productName,
                                       final String hyperLink
    ) {
        super(templateName, recipientEmail, senderEmail, subject);
        propertiesMap = new HashMap<>();
        propertiesMap.put(PROPERTIES_PROMPTING_USER_NAME, promptingUserName);
        propertiesMap.put(PROPERTIES_MENTIONED_USER_NAME, mentionedUserName);
        propertiesMap.put(PROPERTIES_LOCATION, type);
        propertiesMap.put(PROPERTIES_PRODUCT_NAME, productName);
        propertiesMap.put(PROPERTIES_LINK, hyperLink);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MentionUserEmailSendPayload)) {
            return false;
        }
        final MentionUserEmailSendPayload that = (MentionUserEmailSendPayload) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(propertiesMap, that.propertiesMap)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(propertiesMap)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("propertiesMap", propertiesMap)
                .toString();
    }

    @Override
    public Map<String, String> properties() {
        return propertiesMap;
    }
}
