package com.vntana.core.model.client.response.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/20
 * Time: 5:11 PM
 */
public class GetAllOrganizationsResponseModel implements ResponseModel {
    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("organizationName")
    private String organizationName;

    @JsonProperty("clientUuid")
    private String clientUuid;

    @JsonProperty("clientName")
    private String clientName;

    public GetAllOrganizationsResponseModel() {
    }

    public GetAllOrganizationsResponseModel(final String organizationUuid, final String organizationName, final String clientUuid, final String clientName) {
        this.organizationUuid = organizationUuid;
        this.organizationName = organizationName;
        this.clientUuid = clientUuid;
        this.clientName = clientName;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(final String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(final String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GetAllOrganizationsResponseModel that = (GetAllOrganizationsResponseModel) o;
        return new EqualsBuilder()
                .append(organizationUuid, that.organizationUuid)
                .append(organizationName, that.organizationName)
                .append(clientUuid, that.clientUuid)
                .append(clientName, that.clientName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(organizationUuid)
                .append(organizationName)
                .append(clientUuid)
                .append(clientName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("organizationUuid", organizationUuid)
                .append("organizationName", organizationName)
                .append("clientUuid", clientUuid)
                .append("clientName", clientName)
                .toString();
    }
}
