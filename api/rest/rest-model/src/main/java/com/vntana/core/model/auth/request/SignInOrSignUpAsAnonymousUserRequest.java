package com.vntana.core.model.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 7/14/2021
 * Time: 4:53 PM
 */
public class SignInOrSignUpAsAnonymousUserRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {
    
    @JsonProperty("externalUuid")
    private String externalUuid;
    
    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public SignInOrSignUpAsAnonymousUserRequest(final String externalUuid, final String organizationUuid) {
        this.externalUuid = externalUuid;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignInOrSignUpAsAnonymousUserRequest)) {
            return false;
        }
        final SignInOrSignUpAsAnonymousUserRequest that = (SignInOrSignUpAsAnonymousUserRequest) o;
        return new EqualsBuilder()
                .append(externalUuid, that.externalUuid)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(externalUuid)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("externalUuid", externalUuid)
                .append("organizationUuid", organizationUuid)
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
}
