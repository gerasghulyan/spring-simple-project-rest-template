package com.vntana.core.model.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 10:37 AM
 */
public class SignInWithPersonalAccessTokenRequest {
    
    @JsonProperty("personal-access-token")
    private String personalAccessToken;

    public SignInWithPersonalAccessTokenRequest() {
    }

    public SignInWithPersonalAccessTokenRequest(final String personalAccessToken) {
        this.personalAccessToken = personalAccessToken;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignInWithPersonalAccessTokenRequest)) {
            return false;
        }
        final SignInWithPersonalAccessTokenRequest that = (SignInWithPersonalAccessTokenRequest) o;
        return new EqualsBuilder()
                .append(personalAccessToken, that.personalAccessToken)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(personalAccessToken)
                .toHashCode();
    }

    public String getPersonalAccessToken() {
        return personalAccessToken;
    }

    public void setPersonalAccessToken(final String personalAccessToken) {
        this.personalAccessToken = personalAccessToken;
    }
}
