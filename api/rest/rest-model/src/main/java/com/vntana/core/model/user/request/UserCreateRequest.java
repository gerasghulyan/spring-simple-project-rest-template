package com.vntana.core.model.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.api.models.request.ValidatableRequest;
import com.vntana.core.model.commons.request.AbstractRequestModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:40 PM
 */
public class UserCreateRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("name")
    private String clientName;

    @JsonProperty("slug")
    private String clientSlug;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    public UserCreateRequest() {
    }

    public UserCreateRequest(final String organizationUuid, final String clientName, final String clientSlug, final String fullName, final String email, final String password) {
        this.organizationUuid = organizationUuid;
        this.clientName = clientName;
        this.clientSlug = clientSlug;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        final List<UserErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(organizationUuid)) {
            errors.add(UserErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        if (StringUtils.isBlank(clientName)) {
            errors.add(UserErrorResponseModel.MISSING_CLIENT_NAME);
        }
        if (StringUtils.isBlank(clientSlug)) {
            errors.add(UserErrorResponseModel.MISSING_CLIENT_SLUG);
        }
        if (StringUtils.isBlank(fullName)) {
            errors.add(UserErrorResponseModel.MISSING_FULL_NAME);
        }
        if (StringUtils.isBlank(email)) {
            errors.add(UserErrorResponseModel.MISSING_EMAIL);
        }
        if (StringUtils.isBlank(password)) {
            errors.add(UserErrorResponseModel.MISSING_PASSWORD);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCreateRequest)) {
            return false;
        }
        final UserCreateRequest that = (UserCreateRequest) o;
        return new EqualsBuilder()
                .append(organizationUuid, that.organizationUuid)
                .append(clientName, that.clientName)
                .append(clientSlug, that.clientSlug)
                .append(fullName, that.fullName)
                .append(email, that.email)
                .append(password, that.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationUuid)
                .append(clientName)
                .append(clientSlug)
                .append(fullName)
                .append(email)
                .append(password)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("organizationUuid", organizationUuid)
                .append("clientName", clientName)
                .append("clientSlug", clientSlug)
                .append("fullName", fullName)
                .append("email", email)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(final String clientName) {
        this.clientName = clientName;
    }

    public String getClientSlug() {
        return clientSlug;
    }

    public void setClientSlug(final String clientSlug) {
        this.clientSlug = clientSlug;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
