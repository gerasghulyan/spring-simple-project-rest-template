package com.vntana.core.model.invitation.organization.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.LinkedList;
import java.util.List;

import static com.vntana.core.model.utils.EmailSanitizerUtility.sanitize;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:07 PM
 */
public class CreateInvitationOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<InvitationOrganizationErrorResponseModel> {

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

    public CreateInvitationOrganizationRequest() {
        super();
    }

    public CreateInvitationOrganizationRequest(final String ownerFullName,
                                               final String email,
                                               final String organizationName,
                                               final String slug,
                                               final String customerSubscriptionDefinitionUuid) {
        super();
        this.ownerFullName = ownerFullName;
        this.email = sanitize(email);
        this.organizationName = organizationName;
        this.slug = slug;
        this.customerSubscriptionDefinitionUuid = customerSubscriptionDefinitionUuid;
    }

    @Override
    public List<InvitationOrganizationErrorResponseModel> validate() {
        final List<InvitationOrganizationErrorResponseModel> errors = new LinkedList<>();
        if (StringUtils.isEmpty(ownerFullName)) {
            errors.add(InvitationOrganizationErrorResponseModel.MISSING_OWNER_FULL_NAME);
        }
        if (StringUtils.isEmpty(email)) {
            errors.add(InvitationOrganizationErrorResponseModel.MISSING_EMAIL);
        }
        if (StringUtils.isEmpty(organizationName)) {
            errors.add(InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_NAME);
        }
        if (StringUtils.isEmpty(slug)) {
            errors.add(InvitationOrganizationErrorResponseModel.MISSING_SLUG);
        }
        if (StringUtils.isEmpty(customerSubscriptionDefinitionUuid)) {
            errors.add(InvitationOrganizationErrorResponseModel.MISSING_CUSTOMER_SUBSCRIPTION_DEFINITION_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateInvitationOrganizationRequest)) {
            return false;
        }
        final CreateInvitationOrganizationRequest that = (CreateInvitationOrganizationRequest) o;
        return new EqualsBuilder()
                .append(ownerFullName, that.ownerFullName)
                .append(email, that.email)
                .append(organizationName, that.organizationName)
                .append(slug, that.slug)
                .append(customerSubscriptionDefinitionUuid, that.customerSubscriptionDefinitionUuid)
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
}
