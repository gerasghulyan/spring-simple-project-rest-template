package com.vntana.core.model.invitation.organization.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import com.vntana.core.model.invitation.InvitationStatusModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 2:23 PM
 */
public class GetInvitationOrganizationResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("ownerFullName")
    private String ownerFullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("organizationName")
    private String organizationName;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("customerSubscriptionDefinitionUuid")
    private String customerSubscriptionDefinitionUuid;

    @JsonProperty("status")
    private InvitationStatusModel status;

    public GetInvitationOrganizationResponseModel() {
        super();
    }

    public GetInvitationOrganizationResponseModel(final String uuid,
                                                  final String ownerFullName,
                                                  final String email,
                                                  final String organizationName,
                                                  final String slug,
                                                  final String customerSubscriptionDefinitionUuid,
                                                  final InvitationStatusModel status) {
        super(uuid);
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
        if (!(o instanceof GetInvitationOrganizationResponseModel)) {
            return false;
        }
        final GetInvitationOrganizationResponseModel that = (GetInvitationOrganizationResponseModel) o;
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

    public void setOwnerFullName(final String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(final String slug) {
        this.slug = slug;
    }

    public String getCustomerSubscriptionDefinitionUuid() {
        return customerSubscriptionDefinitionUuid;
    }

    public void setCustomerSubscriptionDefinitionUuid(final String customerSubscriptionDefinitionUuid) {
        this.customerSubscriptionDefinitionUuid = customerSubscriptionDefinitionUuid;
    }

    public InvitationStatusModel getStatus() {
        return status;
    }

    public void setStatus(final InvitationStatusModel status) {
        this.status = status;
    }
}
