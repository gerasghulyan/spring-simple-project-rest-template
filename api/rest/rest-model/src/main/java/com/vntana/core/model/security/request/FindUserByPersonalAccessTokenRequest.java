package com.vntana.core.model.security.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Diana Gevorgyan
 * Date: 3/29/21
 * Time: 10:35 AM
 */
public class FindUserByPersonalAccessTokenRequest extends AbstractRequestModel implements ValidatableRequest<TokenAuthenticationErrorResponseModel> {

    @JsonProperty("token")
    private String token;

    public FindUserByPersonalAccessTokenRequest() {
    }

    public FindUserByPersonalAccessTokenRequest(final String token) {
        this.token = token;
    }

    @Override
    public List<TokenAuthenticationErrorResponseModel> validate() {
        final List<TokenAuthenticationErrorResponseModel> errors = new LinkedList<>();
        if (Objects.isNull(token)) {
            errors.add(TokenAuthenticationErrorResponseModel.MISSING_TOKEN);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FindUserByPersonalAccessTokenRequest)) {
            return false;
        }
        final FindUserByPersonalAccessTokenRequest that = (FindUserByPersonalAccessTokenRequest) o;
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
