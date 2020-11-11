package com.vntana.core.model.user.role.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

/**
 * Created by Diana Gevorgyan
 * Date: 11/5/20
 * Time: 10:10 AM
 */
public class UserRoleGrantClientOrganizationRequest extends AbstractUserRoleGrantClientOrganizationAwareRequest {

    @JsonProperty("userRole")
    private UserRoleModel userRole;

    public UserRoleGrantClientOrganizationRequest() {
        super();
    }

    public UserRoleGrantClientOrganizationRequest(final String userUuid, final String clientOrganizationUuid, final UserRoleModel userRole) {
        super(userUuid, clientOrganizationUuid);
        this.userRole = userRole;
    }

    @Override
    public List<UserRoleErrorResponseModel> validate() {
        final List<UserRoleErrorResponseModel> errors = super.validate();
        if (Objects.isNull(userRole)) {
            errors.add(UserRoleErrorResponseModel.MISSING_CLIENT_ROLE);
        }
        if (!userRole.isClientRelatedRole()) {
            errors.add(UserRoleErrorResponseModel.REQUEST_ROLE_IS_NOT_CLIENT_RELATED);
        }
        return errors;
    }

    public UserRoleModel getUserRole() {
        return userRole;
    }

    public void setUserRole(final UserRoleModel userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof UserRoleGrantClientOrganizationRequest)) return false;

        final UserRoleGrantClientOrganizationRequest that = (UserRoleGrantClientOrganizationRequest) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(userRole, that.userRole)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(userRole)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userRole", userRole)
                .toString();
    }
}