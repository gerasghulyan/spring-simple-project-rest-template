package com.vntana.core.model.user.request;

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
 * Date: 10/1/19
 * Time: 6:40 PM
 */
public class CreateUserRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("organizationName")
    private String organizationName;

    @JsonProperty("organizationSlug")
    private String organizationSlug;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("token")
    private String token;

    public CreateUserRequest() {
    }

    public CreateUserRequest(final String organizationName, final String organizationSlug, final String fullName, final String email, final String password, final String token) {
        this.organizationName = organizationName;
        this.organizationSlug = organizationSlug;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        final List<UserErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(organizationName)) {
            errors.add(UserErrorResponseModel.MISSING_CLIENT_NAME);
        }
        if (StringUtils.isBlank(organizationSlug)) {
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
        if (StringUtils.isBlank(token)) {
            errors.add(UserErrorResponseModel.MISSING_VERIFICATION_TOKEN);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateUserRequest)) {
            return false;
        }
        final CreateUserRequest that = (CreateUserRequest) o;
        return new EqualsBuilder()
                .append(organizationName, that.organizationName)
                .append(organizationSlug, that.organizationSlug)
                .append(fullName, that.fullName)
                .append(email, that.email)
                .append(password, that.password)
                .append(token, that.token)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationName)
                .append(organizationSlug)
                .append(fullName)
                .append(email)
                .append(password)
                .append(token)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("clientName", organizationName)
                .append("clientSlug", organizationSlug)
                .append("fullName", fullName)
                .append("email", email)
                .append("token", token)
                .toString();
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationSlug() {
        return organizationSlug;
    }

    public void setOrganizationSlug(final String organizationSlug) {
        this.organizationSlug = organizationSlug;
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

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
