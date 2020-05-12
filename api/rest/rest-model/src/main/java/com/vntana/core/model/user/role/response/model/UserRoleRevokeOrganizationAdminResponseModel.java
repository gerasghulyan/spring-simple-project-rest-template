package com.vntana.core.model.user.role.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 4:33 PM
 */
public class UserRoleRevokeOrganizationAdminResponseModel implements ResponseModel {

    @JsonProperty("userUuid")
    private String userUuid;

    public UserRoleRevokeOrganizationAdminResponseModel() {
        super();
    }

    public UserRoleRevokeOrganizationAdminResponseModel(final String userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRoleRevokeOrganizationAdminResponseModel)) {
            return false;
        }
        final UserRoleRevokeOrganizationAdminResponseModel that = (UserRoleRevokeOrganizationAdminResponseModel) o;
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
                .append("userUuid", userUuid)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }
}
