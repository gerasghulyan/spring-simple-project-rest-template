package com.vntana.core.model.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Geras Ghulyan
 * Date: 18/10/2019
 * Time: 12:06
 */
public class RefreshTokenRequest {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public RefreshTokenRequest() {
    }

    public RefreshTokenRequest(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefreshTokenRequest)) {
            return false;
        }
        final RefreshTokenRequest that = (RefreshTokenRequest) o;
        return new EqualsBuilder()
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }
}
