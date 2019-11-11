package com.vntana.core.model.security.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:08 PM
 */
public class SecureUserOrganizationResponseModel implements ResponseModel {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("username")
    private String username;

    @JsonProperty("userRole")
    private UserRoleModel userRole;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public SecureUserOrganizationResponseModel() {
    }

    public SecureUserOrganizationResponseModel(final String uuid, final String username, final UserRoleModel userRole, final String organizationUuid) {
        this.uuid = uuid;
        this.username = username;
        this.userRole = userRole;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecureUserOrganizationResponseModel)) {
            return false;
        }
        final SecureUserOrganizationResponseModel that = (SecureUserOrganizationResponseModel) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(username, that.username)
                .append(userRole, that.userRole)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(username)
                .append(userRole)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("username", username)
                .append("userRole", userRole)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public UserRoleModel getUserRole() {
        return userRole;
    }

    public void setUserRole(final UserRoleModel userRole) {
        this.userRole = userRole;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }
}
