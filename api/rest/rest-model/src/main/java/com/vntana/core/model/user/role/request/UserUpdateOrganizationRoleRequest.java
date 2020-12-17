package com.vntana.core.model.user.role.request;

import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Vardan Aivazian
 * Date: 14.12.2020
 * Time: 16:29
 */
public class UserUpdateOrganizationRoleRequest extends AbstractUserUpdateRolesRequest {

    private UserRoleModel userRoleModel;

    public UserUpdateOrganizationRoleRequest() {
        super();
    }

    public UserUpdateOrganizationRoleRequest(
            final String userUuid,
            final String organizationUuid,
            final String requestedUserUuid) {
        super(userUuid, organizationUuid, requestedUserUuid);
        this.userRoleModel = UserRoleModel.ORGANIZATION_ADMIN;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserUpdateOrganizationRoleRequest)) {
            return false;
        }
        final UserUpdateOrganizationRoleRequest that = (UserUpdateOrganizationRoleRequest) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(userRoleModel, that.userRoleModel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(userRoleModel)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userRoleModel", userRoleModel)
                .toString();
    }

    public UserRoleModel getUserRoleModel() {
        return userRoleModel;
    }

    public void setUserRoleModel(final UserRoleModel userRoleModel) {
        this.userRoleModel = userRoleModel;
    }
}
