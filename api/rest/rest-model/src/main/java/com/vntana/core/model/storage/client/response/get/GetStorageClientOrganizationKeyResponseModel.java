package com.vntana.core.model.storage.client.response.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 17:24
 */
public class GetStorageClientOrganizationKeyResponseModel implements ResponseModel {

    @JsonProperty("clientUuid")
    private String clientUuid;

    @JsonProperty("name")
    private String name;

    public GetStorageClientOrganizationKeyResponseModel() {
    }

    public GetStorageClientOrganizationKeyResponseModel(final String clientUuid, final String name) {
        this.clientUuid = clientUuid;
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetStorageClientOrganizationKeyResponseModel)) {
            return false;
        }
        final GetStorageClientOrganizationKeyResponseModel that = (GetStorageClientOrganizationKeyResponseModel) o;
        return new EqualsBuilder()
                .append(clientUuid, that.clientUuid)
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(clientUuid)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("clientUuid", clientUuid)
                .append("name", name)
                .toString();
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(final String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
