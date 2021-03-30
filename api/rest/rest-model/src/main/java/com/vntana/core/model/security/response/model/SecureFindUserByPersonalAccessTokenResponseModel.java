package com.vntana.core.model.security.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 3/29/21
 * Time: 10:53 AM
 */
public class SecureFindUserByPersonalAccessTokenResponseModel implements ResponseModel {

    //region Properties
    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("username")
    private String username;

    @JsonProperty("roles")
    private List<UserRoleModel> roles;
    //endregion

    //region Constructors
    public SecureFindUserByPersonalAccessTokenResponseModel() {
        super();
    }

    public SecureFindUserByPersonalAccessTokenResponseModel(final String uuid, final String username, final List<UserRoleModel> roles) {
        this.uuid = uuid;
        this.username = username;
        this.roles = roles;
    }
    //endregion

    //region Equals, HashCode and ToString
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecureFindUserByPersonalAccessTokenResponseModel)) {
            return false;
        }
        final SecureFindUserByPersonalAccessTokenResponseModel that = (SecureFindUserByPersonalAccessTokenResponseModel) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(username, that.username)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(username)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("username", username)
                .append("roles", roles)
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

    public List<UserRoleModel> getRoles() {
        return roles;
    }

    public void setRoles(final List<UserRoleModel> roles) {
        this.roles = roles;
    }
    //endregion
}
