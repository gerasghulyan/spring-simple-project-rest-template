package com.vntana.core.model.invitation.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 11/25/20
 * Time: 2:37 PM
 */
public abstract class AbstractAcceptUserInvitationResponseModel implements ResponseModel {

    @JsonProperty("userUuid")
    private String userUuid;

    @JsonProperty("userRole")
    private UserRoleModel userRoleModel;

    public AbstractAcceptUserInvitationResponseModel() {
        super();
    }

    public AbstractAcceptUserInvitationResponseModel(final String userUuid, final UserRoleModel userRoleModel) {
        this.userUuid = userUuid;
        this.userRoleModel = userRoleModel;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractAcceptUserInvitationResponseModel)) {
            return false;
        }
        final AbstractAcceptUserInvitationResponseModel that = (AbstractAcceptUserInvitationResponseModel) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(userRoleModel, that.userRoleModel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(userRoleModel)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userUuid", userUuid)
                .append("userRoleModel", userRoleModel)
                .toString();
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
