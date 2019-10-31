package com.vntana.core.model.security.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:06 PM
 */
public class FindUserByEmailAndOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("email")
    private String email;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public FindUserByEmailAndOrganizationRequest() {
    }

    public FindUserByEmailAndOrganizationRequest(final String email, final String organizationUuid) {
        this.email = email;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        final List<UserErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(email)) {
            errors.add(UserErrorResponseModel.MISSING_EMAIL);
        }
        if (StringUtils.isBlank(organizationUuid)) {
            errors.add(UserErrorResponseModel.MISSING_ORGANIZATION);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FindUserByEmailAndOrganizationRequest)) {
            return false;
        }
        final FindUserByEmailAndOrganizationRequest that = (FindUserByEmailAndOrganizationRequest) o;
        return new EqualsBuilder()
                .append(email, that.email)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }
}
