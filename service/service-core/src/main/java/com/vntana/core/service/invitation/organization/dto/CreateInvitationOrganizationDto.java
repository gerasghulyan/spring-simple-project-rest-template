package com.vntana.core.service.invitation.organization.dto;

import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 3:23 PM
 */
public class CreateInvitationOrganizationDto implements ServiceDto {

    private final String ownerFullName;
    private final String email;
    private final String organizationName;
    private final String slug;
    private final String customerSubscriptionDefinitionUuid;
    private final InvitationStatus status;

    public CreateInvitationOrganizationDto(final String ownerFullName,
                                           final String email,
                                           final String organizationName,
                                           final String slug,
                                           final String customerSubscriptionDefinitionUuid,
                                           final InvitationStatus status) {
        Assert.hasText(ownerFullName, "The ownerFullName should not be null or empty");
        Assert.hasText(email, "The email should not be null or empty");
        Assert.hasText(organizationName, "The organizationName should not be null or empty");
        Assert.hasText(slug, "The slug should not be null or empty");
        Assert.hasText(customerSubscriptionDefinitionUuid, "The customerSubscriptionDefinitionUuid should not be null or empty");
        Assert.notNull(status, "The status should not be null");
        this.ownerFullName = ownerFullName;
        this.email = email;
        this.organizationName = organizationName;
        this.slug = slug;
        this.customerSubscriptionDefinitionUuid = customerSubscriptionDefinitionUuid;
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateInvitationOrganizationDto)) {
            return false;
        }
        final CreateInvitationOrganizationDto that = (CreateInvitationOrganizationDto) o;
        return new EqualsBuilder()
                .append(ownerFullName, that.ownerFullName)
                .append(email, that.email)
                .append(organizationName, that.organizationName)
                .append(slug, that.slug)
                .append(customerSubscriptionDefinitionUuid, that.customerSubscriptionDefinitionUuid)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(ownerFullName)
                .append(email)
                .append(organizationName)
                .append(slug)
                .append(customerSubscriptionDefinitionUuid)
                .append(status)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ownerFullName", ownerFullName)
                .append("email", email)
                .append("organizationName", organizationName)
                .append("slug", slug)
                .append("customerSubscriptionDefinitionUuid", customerSubscriptionDefinitionUuid)
                .append("status", status)
                .toString();
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public String getEmail() {
        return email;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getSlug() {
        return slug;
    }

    public String getCustomerSubscriptionDefinitionUuid() {
        return customerSubscriptionDefinitionUuid;
    }

    public InvitationStatus getStatus() {
        return status;
    }
}
