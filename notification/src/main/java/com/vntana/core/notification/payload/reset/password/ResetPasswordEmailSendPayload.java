package com.vntana.core.notification.payload.reset.password;

import com.vntana.core.notification.payload.TemplateEmailSendPayload;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 2:18 PM
 */
public class ResetPasswordEmailSendPayload implements TemplateEmailSendPayload {

    private final String templateName;
    private final String recipientEmail;
    private final String senderEmail;
    private final String subject;
    private final String verificationLinkAddress;

    public ResetPasswordEmailSendPayload(final String templateName,
                                         final String recipientEmail,
                                         final String senderEmail,
                                         final String subject,
                                         final String verificationLinkAddress) {
        this.templateName = templateName;
        this.recipientEmail = recipientEmail;
        this.senderEmail = senderEmail;
        this.subject = subject;
        this.verificationLinkAddress = verificationLinkAddress;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResetPasswordEmailSendPayload)) {
            return false;
        }
        final ResetPasswordEmailSendPayload that = (ResetPasswordEmailSendPayload) o;
        return new EqualsBuilder()
                .append(templateName, that.templateName)
                .append(recipientEmail, that.recipientEmail)
                .append(senderEmail, that.senderEmail)
                .append(subject, that.subject)
                .append(verificationLinkAddress, that.verificationLinkAddress)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(templateName)
                .append(recipientEmail)
                .append(senderEmail)
                .append(subject)
                .append(verificationLinkAddress)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("templateName", templateName)
                .append("recipientEmail", recipientEmail)
                .append("senderEmail", senderEmail)
                .append("subject", subject)
                .append("verificationLinkAddress", verificationLinkAddress)
                .toString();
    }

    @Override
    public String templateName() {
        return templateName;
    }

    @Override
    public List<String> recipientsEmails() {
        return Collections.singletonList(recipientEmail);
    }

    @Override
    public String senderEmail() {
        return senderEmail;
    }

    @Override
    public String subject() {
        return subject;
    }

    @Override
    public Map<String, String> properties() {
        return Collections.singletonMap("link", verificationLinkAddress);
    }
}
