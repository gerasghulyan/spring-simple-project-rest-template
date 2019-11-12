package com.vntana.core.notification.payload.verification;

import com.vntana.core.notification.payload.TemplateEmailSendPayload;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/12/19
 * Time: 12:26 PM
 */
public class VerificationEmailSendPayload implements TemplateEmailSendPayload {

    private final String templateName;
    private final String recipientsEmail;

    @Value("email.send.from")
    private String senderEmail;

    private final String subject;
    private final String verificationLinkAddress;

    public VerificationEmailSendPayload(final String templateName,
                                        final String recipientsEmail,
                                        final String subject,
                                        final String verificationLinkAddress) {
        this.templateName = templateName;
        this.recipientsEmail = recipientsEmail;
        this.subject = subject;
        this.verificationLinkAddress = verificationLinkAddress;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VerificationEmailSendPayload)) {
            return false;
        }
        final VerificationEmailSendPayload that = (VerificationEmailSendPayload) o;
        return new EqualsBuilder()
                .append(templateName, that.templateName)
                .append(recipientsEmail, that.recipientsEmail)
                .append(senderEmail, that.senderEmail)
                .append(subject, that.subject)
                .append(verificationLinkAddress, that.verificationLinkAddress)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(templateName)
                .append(recipientsEmail)
                .append(senderEmail)
                .append(subject)
                .append(verificationLinkAddress)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("templateName", templateName)
                .append("recipientsEmail", recipientsEmail)
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
        return Collections.singletonList(recipientsEmail);
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
