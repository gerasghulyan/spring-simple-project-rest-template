package com.vntana.core.model.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Geras Ghulyan.
 * Date: 10/10/19
 * Time: 10:40 AM
 */
public class GetClientsByUserAndBulkOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<ClientOrganizationErrorResponseModel> {

    @JsonProperty("organizationUuid")
    private List<String> organizationsUuids;

    public GetClientsByUserAndBulkOrganizationRequest() {
    }

    public GetClientsByUserAndBulkOrganizationRequest(final List<String> organizationsUuids) {
        this.organizationsUuids = organizationsUuids;
    }

    @Override
    public List<ClientOrganizationErrorResponseModel> validate() {
        final List<ClientOrganizationErrorResponseModel> errors = initializeNew();
        if (CollectionUtils.isEmpty(getOrganizationsUuids())) {
            errors.add(ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        return errors;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("organizationsUuids", organizationsUuids)
                .toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetClientsByUserAndBulkOrganizationRequest)) {
            return false;
        }
        final GetClientsByUserAndBulkOrganizationRequest that = (GetClientsByUserAndBulkOrganizationRequest) o;
        return new EqualsBuilder()
                .append(organizationsUuids, that.organizationsUuids)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationsUuids)
                .toHashCode();
    }

    public List<String> getOrganizationsUuids() {
        return Optional.ofNullable(organizationsUuids).orElse(Collections.emptyList());
    }

    public void setOrganizationsUuids(final List<String> organizationsUuids) {
        this.organizationsUuids = organizationsUuids;
    }
}
