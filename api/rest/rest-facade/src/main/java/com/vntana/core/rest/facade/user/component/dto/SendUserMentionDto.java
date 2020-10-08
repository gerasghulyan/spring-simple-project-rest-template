package com.vntana.core.rest.facade.user.component.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 07.10.2020
 * Time: 11:36
 */
public class SendUserMentionDto {

    private final String email;

    private final String promptingUserName;

    private final String mentionedUserName;
    
    private final UserMentionedEntityType entityType;

    private final String entityUuid;
    
    private final String productUuid;
    
    private final String productName;

    private final String clientSlug;
    
    private final String organizationSlug;

    public SendUserMentionDto(final String email,
                              final String promptingUserName,
                              final String mentionedUserName,
                              final UserMentionedEntityType entityType,
                              final String entityUuid,
                              final String productUuid,
                              final String productName,
                              final String clientSlug,
                              final String organizationSlug) {
        Assert.hasText(email, "The email should not be null or empty");
        Assert.hasText(promptingUserName, "The prompting user full name should not be null or empty");
        Assert.hasText(mentionedUserName, "The mentioned user full name should not be null or empty");
        Assert.notNull(entityType, "The UserMentionedEntityType should not be null");
        Assert.hasText(entityUuid, "The entityUuid should not be null or empty");
        Assert.hasText(productUuid, "The productUuid should not be null or empty");
        Assert.hasText(productName, "The productName should not be null or empty");
        Assert.hasText(clientSlug, "The clientSlug should not be null or empty");
        Assert.hasText(organizationSlug, "The organizationSlug should not be null or empty");
        this.email = email;
        this.promptingUserName = promptingUserName;
        this.mentionedUserName = mentionedUserName;
        this.entityType = entityType;
        this.entityUuid = entityUuid;
        this.productUuid = productUuid;
        this.productName = productName;
        this.clientSlug = clientSlug;
        this.organizationSlug = organizationSlug;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SendUserMentionDto)) {
            return false;
        }
        final SendUserMentionDto that = (SendUserMentionDto) o;
        return new EqualsBuilder()
                .append(email, that.email)
                .append(promptingUserName, that.promptingUserName)
                .append(mentionedUserName, that.mentionedUserName)
                .append(entityType, that.entityType)
                .append(entityUuid, that.entityUuid)
                .append(productUuid, that.productUuid)
                .append(productName, that.productName)
                .append(clientSlug, that.clientSlug)
                .append(organizationSlug, that.organizationSlug)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .append(promptingUserName)
                .append(mentionedUserName)
                .append(entityType)
                .append(entityUuid)
                .append(productUuid)
                .append(productName)
                .append(clientSlug)
                .append(organizationSlug)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("email", email)
                .append("promptingUserName", promptingUserName)
                .append("mentionedUserName", mentionedUserName)
                .append("entityType", entityType)
                .append("entityUuid", entityUuid)
                .append("productUuid", productUuid)
                .append("productName", productName)
                .append("clientSlug", clientSlug)
                .append("organizationSlug", organizationSlug)
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public String getPromptingUserName() {
        return promptingUserName;
    }

    public String getMentionedUserName() {
        return mentionedUserName;
    }

    public UserMentionedEntityType getEntityType() {
        return entityType;
    }

    public String getEntityUuid() {
        return entityUuid;
    }

    public String getProductUuid() {
        return productUuid;
    }

    public String getProductName() {
        return productName;
    }

    public String getClientSlug() {
        return clientSlug;
    }

    public String getOrganizationSlug() {
        return organizationSlug;
    }
}
