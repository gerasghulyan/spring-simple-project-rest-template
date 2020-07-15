package com.vntana.core.model.token.auth.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 2:23 PM
 */
public class AuthTokenFindByTokenResponseModel implements ResponseModel {

    @JsonProperty("userUuid")
    private String userUuid;

    @JsonProperty("userEmail")
    private String userEmail;

    public AuthTokenFindByTokenResponseModel() {
    }

    public AuthTokenFindByTokenResponseModel(final String userUuid, final String userEmail) {
        this.userUuid = userUuid;
        this.userEmail = userEmail;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthTokenFindByTokenResponseModel)) {
            return false;
        }
        final AuthTokenFindByTokenResponseModel that = (AuthTokenFindByTokenResponseModel) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(userEmail, that.userEmail)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(userEmail)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userUuid", userUuid)
                .append("userEmail", userEmail)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(final String userEmail) {
        this.userEmail = userEmail;
    }
}
