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
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 1:30 AM
 */
public class CreatePersonalAccessTokenRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("token")
    private String token;

    @JsonProperty("userUuid")
    private String userUuid;

    public CreatePersonalAccessTokenRequest() {
    }

    public CreatePersonalAccessTokenRequest(final String token, final String userUuid) {
        this.token = token;
        this.userUuid = userUuid;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        final List<UserErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(token)) {
            errors.add(UserErrorResponseModel.MISSING_TOKEN);
        }
        if (StringUtils.isBlank(userUuid)) {
            errors.add(UserErrorResponseModel.MISSING_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreatePersonalAccessTokenRequest)) {
            return false;
        }
        final CreatePersonalAccessTokenRequest that = (CreatePersonalAccessTokenRequest) o;
        return new EqualsBuilder()
                .append(token, that.token)
                .append(userUuid, that.userUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(token)
                .append(userUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("token", token)
                .append("userUuid", userUuid)
                .toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
    }
}
