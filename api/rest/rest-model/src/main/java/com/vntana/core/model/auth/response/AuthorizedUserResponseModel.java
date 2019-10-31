package com.vntana.core.model.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Geras Ghulyan.
 * Date: 10/1/19
 * Time: 6:43 PM
 */
public class AuthorizedUserResponseModel implements ResponseModel {

    @JsonProperty("username")
    private String username;

    @JsonProperty("userRole")
    private UserRoleModel userRole;

    @JsonProperty("userRoleType")
    private UserRoleTypeModel userRoleType;

    @JsonProperty("password")
    private String password;

    private AuthorizedUserResponseModel() {
        super();
    }

    public AuthorizedUserResponseModel(final String username, final UserRoleModel userRole, final UserRoleTypeModel userRoleType, final String password) {
        this.username = username;
        this.userRole = userRole;
        this.userRoleType = userRoleType;
        this.password = password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthorizedUserResponseModel)) {
            return false;
        }
        final AuthorizedUserResponseModel that = (AuthorizedUserResponseModel) o;
        return new EqualsBuilder()
                .append(username, that.username)
                .append(userRole, that.userRole)
                .append(userRoleType, that.userRoleType)
                .append(password, that.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(username)
                .append(userRole)
                .append(userRoleType)
                .append(password)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("username", username)
                .append("userRole", userRole)
                .append("userRoleType", userRoleType)
                .append("password", password)
                .toString();
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

    public UserRoleTypeModel getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(final UserRoleTypeModel userRoleType) {
        this.userRoleType = userRoleType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
