package com.vntana.core.notification.payload.invitation.organization;

import com.vntana.core.notification.payload.AbstractTemplateEmailSendPayload;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 5:50 PM
 */
public class InvitationOrganizationEmailSendPayload extends AbstractTemplateEmailSendPayload {

    private final String verificationLinkAddress;

    public InvitationOrganizationEmailSendPayload(final String templateName,
                                                  final String recipientEmail,
                                                  final String senderEmail,
                                                  final String subject,
                                                  final String verificationLinkAddress) {

        super(templateName, recipientEmail, senderEmail, subject);
        this.verificationLinkAddress = verificationLinkAddress;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvitationOrganizationEmailSendPayload)) {
            return false;
        }
        final InvitationOrganizationEmailSendPayload that = (InvitationOrganizationEmailSendPayload) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(verificationLinkAddress, that.verificationLinkAddress)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(verificationLinkAddress)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("verificationLinkAddress", verificationLinkAddress)
                .toString();
    }

    @Override
    public Map<String, String> properties() {
        return Collections.singletonMap("link", verificationLinkAddress);
    }
}