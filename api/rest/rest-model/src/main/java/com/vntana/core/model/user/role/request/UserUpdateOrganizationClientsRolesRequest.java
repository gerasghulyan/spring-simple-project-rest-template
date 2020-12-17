package com.vntana.core.model.user.role.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

/**
 * Created by Vardan Aivazian
 * Date: 10.12.2020
 * Time: 18:22
 */
public class UserUpdateOrganizationClientsRolesRequest extends AbstractUserUpdateRolesRequest {
    
    @JsonProperty("updateClientRoles")
    private List<UpdateClientRoleRequest> updateClientRoles;

    public UserUpdateOrganizationClientsRolesRequest() {
        super();
    }

    public UserUpdateOrganizationClientsRolesRequest(
            final String userUuid,
            final String organizationUuid,
            final String requestedUserUuid,
            final List<UpdateClientRoleRequest> updateClientRoles) {
        super(userUuid, organizationUuid, requestedUserUuid);
        this.updateClientRoles = updateClientRoles;
    }

    @Override
    public List<UserRoleErrorResponseModel> validate() {
        final List<UserRoleErrorResponseModel> errors = super.validate();
        if (!CollectionUtils.isEmpty(updateClientRoles)) {
            final boolean errorClientUuid = updateClientRoles.stream()
                    .anyMatch(updateClientRole -> StringUtils.isBlank(updateClientRole.getClientUuid()));
            if (errorClientUuid) {
                errors.add(UserRoleErrorResponseModel.MISSING_CLIENT_ORGANIZATION_UUID);
            }
            final boolean errorClientRole = updateClientRoles.stream()
                    .anyMatch(updateClientRole -> Objects.isNull(updateClientRole.getClientRole()));
            if (errorClientRole) {
                errors.add(UserRoleErrorResponseModel.MISSING_CLIENT_ROLE);
            } else {
                final boolean errorClientRoleAbility = updateClientRoles.stream()
                        .anyMatch(updateClientRole -> !updateClientRole.getClientRole().hasClientAbility());
                if (errorClientRoleAbility) {
                    errors.add(UserRoleErrorResponseModel.REQUEST_ROLE_IS_NOT_CLIENT_RELATED);
                }
            }
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserUpdateOrganizationClientsRolesRequest)) {
            return false;
        }
        final UserUpdateOrganizationClientsRolesRequest that = (UserUpdateOrganizationClientsRolesRequest) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(updateClientRoles, that.updateClientRoles)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(updateClientRoles)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("updateClientRoles", updateClientRoles)
                .toString();
    }

    public List<UpdateClientRoleRequest> getUpdateClientRoles() {
        return updateClientRoles;
    }

    public void setUpdateClientRoles(final List<UpdateClientRoleRequest> updateClientRoles) {
        this.updateClientRoles = updateClientRoles;
    }
}
