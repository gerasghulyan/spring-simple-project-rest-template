package com.vntana.core.model.token.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Geras Ghulyan
 * Date: 7/15/20
 * Time: 2:03 PM
 */
public class AuthTokenRequest extends AbstractRequestModel implements ValidatableRequest<AuthTokenErrorResponseModel> {

    @JsonProperty("token")
    private String token;

    public AuthTokenRequest() {
    }

    public AuthTokenRequest(final String token) {
        this.token = token;
    }

    @Override
    public List<AuthTokenErrorResponseModel> validate() {
        final List<AuthTokenErrorResponseModel> errors = new LinkedList<>();
        if (Objects.isNull(token)) {
            errors.add(AuthTokenErrorResponseModel.MISSING_TOKEN);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthTokenRequest)) {
            return false;
        }
        final AuthTokenRequest that = (AuthTokenRequest) o;
        return new EqualsBuilder()
                .append(token, that.token)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(token)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("token", token)
                .toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
