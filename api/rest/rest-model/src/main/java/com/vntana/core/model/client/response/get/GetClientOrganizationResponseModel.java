package com.vntana.core.model.client.response.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Geras Ghulyan
 * Date: 10/9/19
 * Time: 2:55 PM
 */
public class GetClientOrganizationResponseModel implements ResponseModel {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("organizationSlug")
    private String organizationSlug;

    @JsonProperty("clientUuid")
    private String clientUuid;

    @JsonProperty("clientSlug")
    private String clientSlug;

    public GetClientOrganizationResponseModel() {
    }

    public GetClientOrganizationResponseModel(final String organizationUuid, final String organizationSlug, final String clientUuid, final String clientSlug) {
        this.organizationUuid = organizationUuid;
        this.organizationSlug = organizationSlug;
        this.clientUuid = clientUuid;
        this.clientSlug = clientSlug;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetClientOrganizationResponseModel)) {
            return false;
        }
        final GetClientOrganizationResponseModel that = (GetClientOrganizationResponseModel) o;
        return new EqualsBuilder()
                .append(organizationUuid, that.organizationUuid)
                .append(organizationSlug, that.organizationSlug)
                .append(clientUuid, that.clientUuid)
                .append(clientSlug, that.clientSlug)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationUuid)
                .append(organizationSlug)
                .append(clientUuid)
                .append(clientSlug)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("organizationUuid", organizationUuid)
                .append("organizationSlug", organizationSlug)
                .append("clientUuid", clientUuid)
                .append("clientSlug", clientSlug)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public String getOrganizationSlug() {
        return organizationSlug;
    }

    public void setOrganizationSlug(final String organizationSlug) {
        this.organizationSlug = organizationSlug;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(final String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public String getClientSlug() {
        return clientSlug;
    }

    public void setClientSlug(final String clientSlug) {
        this.clientSlug = clientSlug;
    }
}
