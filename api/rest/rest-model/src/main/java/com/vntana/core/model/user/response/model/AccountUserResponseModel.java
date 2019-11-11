package com.vntana.core.model.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Geras Ghulyan.
 * Date: 10/18/19
 * Time: 5:08 PM
 */
public class AccountUserResponseModel implements ResponseModel {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("role")
    private UserRoleModel role;

    public AccountUserResponseModel() {
    }

    public AccountUserResponseModel(final String uuid, final String fullName, final String email, final UserRoleModel role) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountUserResponseModel)) {
            return false;
        }
        final AccountUserResponseModel that = (AccountUserResponseModel) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(fullName, that.fullName)
                .append(email, that.email)
                .append(role, that.role)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(fullName)
                .append(email)
                .append(role)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("fullName", fullName)
                .append("email", email)
                .append("role", role)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getfullName() {
        return fullName;
    }

    public void setfullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public UserRoleModel getRole() {
        return role;
    }

    public void setRole(final UserRoleModel role) {
        this.role = role;
    }
}
