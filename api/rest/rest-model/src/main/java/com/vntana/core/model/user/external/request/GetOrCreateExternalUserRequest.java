package com.vntana.core.model.user.external.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by Diana Gevorgyan
 * Date: 7/14/2021
 * Time: 4:53 PM
 */
public class GetOrCreateExternalUserRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("externalUuid")
    private String externalUuid;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("clientOrganizationUuid")
    private String clientOrganizationUuid;

    public GetOrCreateExternalUserRequest() {
    }

    public GetOrCreateExternalUserRequest(
            final String externalUuid,
            final String organizationUuid,
            final String clientOrganizationUuid) {
        this.externalUuid = externalUuid;
        this.organizationUuid = organizationUuid;
        this.clientOrganizationUuid = clientOrganizationUuid;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        if (StringUtils.isBlank(externalUuid)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_UUID);
        }
        if (Objects.isNull(organizationUuid)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_ORGANIZATION);
        }
        if (Objects.isNull(clientOrganizationUuid)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_CLIENT);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetOrCreateExternalUserRequest)) {
            return false;
        }
        final GetOrCreateExternalUserRequest that = (GetOrCreateExternalUserRequest) o;
        return new EqualsBuilder()
                .append(externalUuid, that.externalUuid)
                .append(organizationUuid, that.organizationUuid)
                .append(clientOrganizationUuid, that.clientOrganizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(externalUuid)
                .append(organizationUuid)
                .append(clientOrganizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("externalUuid", externalUuid)
                .append("organizationUuid", organizationUuid)
                .append("clientOrganizationUuid", clientOrganizationUuid)
                .toString();
    }

    public String getExternalUuid() {
        return externalUuid;
    }

    public void setExternalUuid(final String externalUuid) {
        this.externalUuid = externalUuid;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public String getClientOrganizationUuid() {
        return clientOrganizationUuid;
    }

    public void setClientOrganizationUuid(final String clientOrganizationUuid) {
        this.clientOrganizationUuid = clientOrganizationUuid;
    }
}
