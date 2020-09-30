package com.vntana.core.model.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Vardan Aivazian
 * Date: 29.09.2020
 * Time: 14:43
 */
public class GetUserByUuidsAndOrganizationUuidResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("imageBlobId")
    private String imageBlobId;

    @JsonProperty("inOrganization")
    private boolean inOrganization;

    public GetUserByUuidsAndOrganizationUuidResponseModel() {
        super();
    }

    public GetUserByUuidsAndOrganizationUuidResponseModel(final String uuid, final String fullName, final String email, final String imageBlobId, final boolean inOrganization) {
        super(uuid);
        this.fullName = fullName;
        this.email = email;
        this.imageBlobId = imageBlobId;
        this.inOrganization = inOrganization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetUserByUuidsAndOrganizationUuidResponseModel)) {
            return false;
        }
        final GetUserByUuidsAndOrganizationUuidResponseModel that = (GetUserByUuidsAndOrganizationUuidResponseModel) o;
        return new EqualsBuilder()
                .append(inOrganization, that.inOrganization)
                .append(fullName, that.fullName)
                .append(email, that.email)
                .append(imageBlobId, that.imageBlobId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(fullName)
                .append(email)
                .append(imageBlobId)
                .append(inOrganization)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("fullName", fullName)
                .append("email", email)
                .append("imageBlobId", imageBlobId)
                .append("inOrganization", inOrganization)
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

    public boolean isInOrganization() {
        return inOrganization;
    }

    public void setInOrganization(final boolean inOrganization) {
        this.inOrganization = inOrganization;
    }
}
