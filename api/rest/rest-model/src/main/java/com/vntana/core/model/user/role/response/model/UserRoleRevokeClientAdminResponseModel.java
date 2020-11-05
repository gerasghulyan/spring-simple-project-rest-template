package com.vntana.core.model.user.role.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 11/5/20
 * Time: 11:35 AM
 */
public class UserRoleRevokeClientAdminResponseModel implements ResponseModel {

    @JsonProperty("userUuid")
    private String userUuid;

    public UserRoleRevokeClientAdminResponseModel() {
        super();
    }

    public UserRoleRevokeClientAdminResponseModel(final String userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRoleRevokeClientAdminResponseModel)) {
            return false;
        }
        final UserRoleRevokeClientAdminResponseModel that = (UserRoleRevokeClientAdminResponseModel) o;
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
}
