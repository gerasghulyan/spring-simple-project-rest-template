package com.vntana.core.model.invitation.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 5:31 PM
 */
public class AcceptInvitationUserToOrganizationResponseModel implements ResponseModel {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("userUuid")
    private String userUuid;

    @JsonProperty("userRole")
    private UserRoleModel userRoleModel;

    public AcceptInvitationUserToOrganizationResponseModel() {
        super();
    }

    public AcceptInvitationUserToOrganizationResponseModel(final String organizationUuid, final String userUuid, final UserRoleModel userRoleModel) {
        this.organizationUuid = organizationUuid;
        this.userUuid = userUuid;
        this.userRoleModel = userRoleModel;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcceptInvitationUserToOrganizationResponseModel)) {
            return false;
        }
        final AcceptInvitationUserToOrganizationResponseModel that = (AcceptInvitationUserToOrganizationResponseModel) o;
        return new EqualsBuilder()
                .append(organizationUuid, that.organizationUuid)
                .append(userUuid, that.userUuid)
                .append(userRoleModel, that.userRoleModel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationUuid)
                .append(userUuid)
                .append(userRoleModel)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("organizationUuid", organizationUuid)
                .append("userUuid", userUuid)
                .append("userRoleModel", userRoleModel)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
    }

    public UserRoleModel getUserRoleModel() {
        return userRoleModel;
    }

    public void setUserRoleModel(final UserRoleModel userRoleModel) {
        this.userRoleModel = userRoleModel;
    }
}
