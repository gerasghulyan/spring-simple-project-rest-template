package com.vntana.core.model.user.response.get.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 4:44 PM
 */
public class GetUsersByOrganizationResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("imageBlobId")
    private String imageBlobId;

    @JsonProperty("role")
    private UserRoleModel userRoleModel;

    public GetUsersByOrganizationResponseModel() {
        super();
    }

    public GetUsersByOrganizationResponseModel(final String uuid,
                                               final String fullName,
                                               final String email,
                                               final String imageBlobId,
                                               final UserRoleModel userRoleModel) {
        super(uuid);
        this.fullName = fullName;
        this.email = email;
        this.imageBlobId = imageBlobId;
        this.userRoleModel = userRoleModel;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof GetUsersByOrganizationResponseModel)) return false;

        final GetUsersByOrganizationResponseModel that = (GetUsersByOrganizationResponseModel) o;

        return new EqualsBuilder()
                .append(getUuid(), that.getUuid())
                .append(fullName, that.fullName)
                .append(email, that.email)
                .append(imageBlobId, that.imageBlobId)
                .append(userRoleModel, that.userRoleModel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getUuid())
                .append(fullName)
                .append(email)
                .append(imageBlobId)
                .append(userRoleModel)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("fullName", fullName)
                .append("email", email)
                .append("imageBlobId", imageBlobId)
                .append("userRoleModel", userRoleModel)
                .toString();
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

    public String getImageBlobId() {
        return imageBlobId;
    }

    public void setImageBlobId(final String imageBlobId) {
        this.imageBlobId = imageBlobId;
    }

    public UserRoleModel getUserRoleModel() {
        return userRoleModel;
    }

    public void setUserRoleModel(final UserRoleModel userRoleModel) {
        this.userRoleModel = userRoleModel;
    }
}
