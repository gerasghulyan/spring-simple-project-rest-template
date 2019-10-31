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
public class SecureFindUserByEmailAndOrganizationResponseModel implements ResponseModel {

    //region Properties
    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("username")
    private String username;

    @JsonProperty("userRole")
    private UserRoleModel userRole;

    @JsonProperty("password")
    private String password;
    //endregion

    //region Constructors
    public SecureFindUserByEmailAndOrganizationResponseModel() {
    }

    public SecureFindUserByEmailAndOrganizationResponseModel(final String uuid, final String username, final UserRoleModel userRole, final String password) {
        this.uuid = uuid;
        this.username = username;
        this.userRole = userRole;
        this.password = password;
    }
    //endregion

    //region Equals, HashCode and ToString
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecureFindUserByEmailAndOrganizationResponseModel)) {
            return false;
        }
        final SecureFindUserByEmailAndOrganizationResponseModel that = (SecureFindUserByEmailAndOrganizationResponseModel) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(username, that.username)
                .append(userRole, that.userRole)
                .append(password, that.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(username)
                .append(userRole)
                .append(password)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("username", username)
                .append("userRole", userRole)
                .append("password", password)
                .toString();
    }
    //endregion

    //region Properties getters and setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
    //endregion

}
