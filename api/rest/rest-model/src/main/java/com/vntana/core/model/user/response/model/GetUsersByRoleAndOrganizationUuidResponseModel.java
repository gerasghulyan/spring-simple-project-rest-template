package com.vntana.core.model.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Manuk Gharslyan.
 * Date: 4/7/2020
 * Time: 1:27 PM
 */
public class GetUsersByRoleAndOrganizationUuidResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    public GetUsersByRoleAndOrganizationUuidResponseModel() {
    }

    public GetUsersByRoleAndOrganizationUuidResponseModel(final String uuid, final String fullName, final String email) {
        super(uuid);
        this.fullName = fullName;
        this.email = email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof GetUsersByRoleAndOrganizationUuidResponseModel)) return false;

        final GetUsersByRoleAndOrganizationUuidResponseModel that = (GetUsersByRoleAndOrganizationUuidResponseModel) o;

        return new EqualsBuilder()
                .append(fullName, that.fullName)
                .append(email, that.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(fullName)
                .append(email)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("fullName", fullName)
                .append("email", email)
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
}