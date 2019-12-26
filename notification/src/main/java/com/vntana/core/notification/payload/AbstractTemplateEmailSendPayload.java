package com.vntana.core.notification.payload;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/25/19
 * Time: 6:00 PM
 */
public abstract class AbstractTemplateEmailSendPayload implements TemplateEmailSendPayload {

    private final String templateName;
    private final String recipientEmail;
    private final String senderEmail;
    private final String subject;

    public AbstractTemplateEmailSendPayload(final String templateName,
                                                  final String recipientEmail,
                                                  final String senderEmail,
                                                  final String subject) {
        this.templateName = templateName;
        this.recipientEmail = recipientEmail;
        this.senderEmail = senderEmail;
        this.subject = subject;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractTemplateEmailSendPayload)) {
            return false;
        }
        final AbstractTemplateEmailSendPayload that = (AbstractTemplateEmailSendPayload) o;
        return new EqualsBuilder()
                .append(templateName, that.templateName)
                .append(recipientEmail, that.recipientEmail)
                .append(senderEmail, that.senderEmail)
                .append(subject, that.subject)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(templateName)
                .append(recipientEmail)
                .append(senderEmail)
                .append(subject)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("templateName", templateName)
                .append("recipientEmail", recipientEmail)
                .append("senderEmail", senderEmail)
                .append("subject", subject)
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
}
