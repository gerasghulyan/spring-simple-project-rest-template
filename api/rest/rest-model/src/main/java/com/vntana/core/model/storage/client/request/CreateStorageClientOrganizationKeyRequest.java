package com.vntana.core.model.storage.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.storage.client.error.StorageClientOrganizationKeyErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 17:23
 */
public class CreateStorageClientOrganizationKeyRequest extends AbstractRequestModel implements ValidatableRequest<StorageClientOrganizationKeyErrorResponseModel> {

    @JsonProperty("clientUuid")
    private String clientUuid;

    public CreateStorageClientOrganizationKeyRequest() {
    }

    public CreateStorageClientOrganizationKeyRequest(final String clientUuid) {
        this.clientUuid = clientUuid;
    }

    @Override
    public List<StorageClientOrganizationKeyErrorResponseModel> validate() {
        final List<StorageClientOrganizationKeyErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(clientUuid)) {
            errors.add(StorageClientOrganizationKeyErrorResponseModel.MISSING_CLIENT_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateStorageClientOrganizationKeyRequest)) {
            return false;
        }
        final CreateStorageClientOrganizationKeyRequest that = (CreateStorageClientOrganizationKeyRequest) o;
        return new EqualsBuilder()
                .append(clientUuid, that.clientUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(clientUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("clientUuid", clientUuid)
                .toString();
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(final String clientUuid) {
        this.clientUuid = clientUuid;
    }
}
