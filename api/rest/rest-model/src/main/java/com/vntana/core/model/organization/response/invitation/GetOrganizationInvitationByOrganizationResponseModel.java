package com.vntana.core.model.organization.response.invitation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Geras Ghulyan
 * Date: 3/26/20
 * Time: 2:23 PM
 */
public class GetOrganizationInvitationByOrganizationResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("customerSubscriptionDefinitionUuid")
    private String customerSubscriptionDefinitionUuid;

    public GetOrganizationInvitationByOrganizationResponseModel() {
        super();
    }

    public GetOrganizationInvitationByOrganizationResponseModel(final String uuid, final String customerSubscriptionDefinitionUuid) {
        super(uuid);
        this.customerSubscriptionDefinitionUuid = customerSubscriptionDefinitionUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetOrganizationInvitationByOrganizationResponseModel)) {
            return false;
        }
        final GetOrganizationInvitationByOrganizationResponseModel that = (GetOrganizationInvitationByOrganizationResponseModel) o;
        return new EqualsBuilder()
                .append(customerSubscriptionDefinitionUuid, that.customerSubscriptionDefinitionUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(customerSubscriptionDefinitionUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("customerSubscriptionDefinitionUuid", customerSubscriptionDefinitionUuid)
                .toString();
    }

    public String getCustomerSubscriptionDefinitionUuid() {
        return customerSubscriptionDefinitionUuid;
    }

    public void setCustomerSubscriptionDefinitionUuid(final String customerSubscriptionDefinitionUuid) {
        this.customerSubscriptionDefinitionUuid = customerSubscriptionDefinitionUuid;
    }
}
