package com.vntana.core.model.user.response.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Vardan Aivazian
 * Date: 04.12.2020
 * Time: 13:57
 */
public class GetUserByOrganizationResponseModel implements ResponseModel {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("role")
    private UserRoleModel userRoleModel;

    @JsonProperty("isEmailVerified")
    private boolean emailVerified;

    @JsonProperty("imageBlobId")
    private String imageBlobId;

    public GetUserByOrganizationResponseModel() {
        super();
    }

    public GetUserByOrganizationResponseModel(
            final String uuid,
            final String fullName,
            final String email,
            final UserRoleModel userRoleModel,
            final boolean emailVerified,
            final String imageBlobId
    ) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.email = email;
        this.userRoleModel = userRoleModel;
        this.emailVerified = emailVerified;
        this.imageBlobId = imageBlobId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetUserByOrganizationResponseModel)) {
            return false;
        }
        final GetUserByOrganizationResponseModel that = (GetUserByOrganizationResponseModel) o;
        return new EqualsBuilder()
                .append(emailVerified, that.emailVerified)
                .append(uuid, that.uuid)
                .append(fullName, that.fullName)
                .append(email, that.email)
                .append(userRoleModel, that.userRoleModel)
                .append(imageBlobId, that.imageBlobId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(fullName)
                .append(email)
                .append(userRoleModel)
                .append(emailVerified)
                .append(imageBlobId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("uuid", uuid)
                .append("fullName", fullName)
                .append("email", email)
                .append("userRoleModel", userRoleModel)
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

    public UserRoleModel getUserRoleModel() {
        return userRoleModel;
    }

    public void setUserRoleModel(final UserRoleModel userRoleModel) {
        this.userRoleModel = userRoleModel;
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
