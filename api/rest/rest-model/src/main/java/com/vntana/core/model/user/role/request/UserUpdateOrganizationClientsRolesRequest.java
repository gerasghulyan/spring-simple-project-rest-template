package com.vntana.core.model.user.role.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

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
            final String authorizedUserUuid,
            final String organizationUuid,
            final String userUuid,
            final List<UpdateClientRoleRequest> updateClientRoles) {
        super(authorizedUserUuid, organizationUuid, userUuid);
        this.updateClientRoles = updateClientRoles;
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
