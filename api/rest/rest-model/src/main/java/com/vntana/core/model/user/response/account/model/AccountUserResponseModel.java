package com.vntana.core.model.user.response.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
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

    @JsonProperty("roles")
    private AccountUserRolesModel roles;

    @JsonProperty("isEmailVerified")
    private boolean emailVerified;

    @JsonProperty("imageBlobId")
    private String imageBlobId;

    public AccountUserResponseModel() {
        super();
    }

    public AccountUserResponseModel(final String uuid,
                                    final String fullName,
                                    final String email,
                                    final AccountUserRolesModel roles,
                                    final boolean emailVerified,
                                    final String imageBlobId) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.email = email;
        this.roles = roles;
        this.emailVerified = emailVerified;
        this.imageBlobId = imageBlobId;
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
                .append(emailVerified, that.emailVerified)
                .append(imageBlobId, that.imageBlobId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(fullName)
                .append(email)
                .append(emailVerified)
                .append(imageBlobId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("fullName", fullName)
                .append("email", email)
                .append("roles", roles)
                .append("emailVerified", emailVerified)
                .append("imageBlobId", imageBlobId)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public AccountUserRolesModel getRoles() {
        return roles;
    }

    public AccountUserResponseModel setRoles(final AccountUserRolesModel roles) {
        this.roles = roles;
        return this;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(final boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getImageBlobId() {
        return imageBlobId;
    }

    public void setImageBlobId(final String imageBlobId) {
        this.imageBlobId = imageBlobId;
    }
}
