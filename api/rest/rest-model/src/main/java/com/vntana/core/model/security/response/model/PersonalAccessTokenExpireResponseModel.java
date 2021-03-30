package com.vntana.core.model.security.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 3/29/21
 * Time: 12:22 PM
 */
public class PersonalAccessTokenExpireResponseModel implements ResponseModel {

    @JsonProperty("userUuid")
    private String userUuid;

    public PersonalAccessTokenExpireResponseModel() {
    }

    public PersonalAccessTokenExpireResponseModel(final String userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalAccessTokenExpireResponseModel)) {
            return false;
        }
        final PersonalAccessTokenExpireResponseModel that = (PersonalAccessTokenExpireResponseModel) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userUuid", userUuid)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
    }
}
