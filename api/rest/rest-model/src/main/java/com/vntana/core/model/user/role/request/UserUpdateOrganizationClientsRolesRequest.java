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
import java.util.Optional;

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
            final Optional<UpdateClientRoleRequest> errorClientUuid = updateClientRoles.stream()
                    .filter(updateClientRole -> StringUtils.isBlank(updateClientRole.getClientUuid()))
                    .findFirst();
            if (errorClientUuid.isPresent()) {
                errors.add(UserRoleErrorResponseModel.MISSING_CLIENT_ORGANIZATION_UUID);
            }
            final Optional<UpdateClientRoleRequest> errorClientRole = updateClientRoles.stream()
                    .filter(updateClientRole -> Objects.isNull(updateClientRole.getClientRole()))
                    .findFirst();
            if (errorClientRole.isPresent()) {
                errors.add(UserRoleErrorResponseModel.MISSING_CLIENT_ROLE);
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
