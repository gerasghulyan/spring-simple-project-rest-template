package com.vntana.core.model.security.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 1:22 AM
 */
public class PersonalAccessTokenResponseModel implements ResponseModel {
    
    @JsonProperty("token")
    private String token;
    
    @JsonProperty("userUuid")
    private String userUuid;

    public PersonalAccessTokenResponseModel() {
        super();
    }

    public PersonalAccessTokenResponseModel(final String token, final String userUuid) {
        this.token = token;
        this.userUuid = userUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalAccessTokenResponseModel)) {
            return false;
        }
        final PersonalAccessTokenResponseModel that = (PersonalAccessTokenResponseModel) o;
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
