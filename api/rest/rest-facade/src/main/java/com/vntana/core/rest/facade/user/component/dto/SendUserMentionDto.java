package com.vntana.core.rest.facade.user.component.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Vardan Aivazian
 * Date: 07.10.2020
 * Time: 11:36
 */
public class SendUserMentionDto {

    private String email;

    private String promptingUserName;

    private String mentionedUserName;
    
    private UserMentionedEntityType entityType;

    private String entityUuid;
    
    private String productUuid;
    
    private String productName;

    private String clientSlug;
    
    private String organizationSlug;

    public SendUserMentionDto() {
        super();
    }

    public SendUserMentionDto(final String email,
                              final String promptingUserName,
                              final String mentionedUserName,
                              final UserMentionedEntityType entityType,
                              final String entityUuid,
                              final String productUuid,
                              final String productName,
                              final String clientSlug,
                              final String organizationSlug) {
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

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPromptingUserName() {
        return promptingUserName;
    }

    public void setPromptingUserName(final String promptingUserName) {
        this.promptingUserName = promptingUserName;
    }

    public String getMentionedUserName() {
        return mentionedUserName;
    }

    public void setMentionedUserName(final String mentionedUserName) {
        this.mentionedUserName = mentionedUserName;
    }

    public UserMentionedEntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(final UserMentionedEntityType entityType) {
        this.entityType = entityType;
    }

    public String getEntityUuid() {
        return entityUuid;
    }

    public void setEntityUuid(final String entityUuid) {
        this.entityUuid = entityUuid;
    }

    public String getProductUuid() {
        return productUuid;
    }

    public void setProductUuid(final String productUuid) {
        this.productUuid = productUuid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public String getClientSlug() {
        return clientSlug;
    }

    public void setClientSlug(final String clientSlug) {
        this.clientSlug = clientSlug;
    }

    public String getOrganizationSlug() {
        return organizationSlug;
    }

    public void setOrganizationSlug(final String organizationSlug) {
        this.organizationSlug = organizationSlug;
    }
}
