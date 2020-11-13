package com.vntana.core.model.security.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/12/2020
 * Time: 11:35 AM
 */
public class SecureUserAwareResponseModel implements ResponseModel {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("username")
    private String username;

    @JsonProperty("userRole")
    private UserRoleModel userRole;

    @JsonProperty("isSuperAdmin")
    private Boolean isSuperAdmin;

    public SecureUserAwareResponseModel() {
        super();
    }

    public SecureUserAwareResponseModel(final String uuid, final String username, final UserRoleModel userRole, final Boolean isSuperAdmin) {
        this.uuid = uuid;
        this.username = username;
        this.userRole = userRole;
        this.isSuperAdmin = isSuperAdmin;
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

    public Boolean getSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(final Boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof SecureUserAwareResponseModel)) return false;

        final SecureUserAwareResponseModel that = (SecureUserAwareResponseModel) o;

        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(username, that.username)
                .append(userRole, that.userRole)
                .append(isSuperAdmin, that.isSuperAdmin)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(username)
                .append(userRole)
                .append(isSuperAdmin)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("username", username)
                .append("userRole", userRole)
                .append("isSuperAdmin", isSuperAdmin)
                .toString();
    }
}