package com.vntana.core.model.user.role.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Vardan Aivazian
 * Date: 10.12.2020
 * Time: 18:28
 */
public class UserUpdateRolesResponseModel implements ResponseModel {

    @JsonProperty("userUuid")
    private String userUuid;

    public UserUpdateRolesResponseModel() {
        super();
    }

    public UserUpdateRolesResponseModel(final String userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserUpdateRolesResponseModel)) {
            return false;
        }
        final UserUpdateRolesResponseModel that = (UserUpdateRolesResponseModel) o;
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
