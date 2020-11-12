package com.vntana.core.model.security.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:08 PM
 */
public class SecureUserOrganizationAwareResponseModel extends SecureUserAwareResponseModel {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public SecureUserOrganizationAwareResponseModel() {
        super();
    }

    public SecureUserOrganizationAwareResponseModel(final String uuid,
                                                    final String username,
                                                    final UserRoleModel userRole,
                                                    final Boolean isSuperAdmin,
                                                    final String organizationUuid) {
        super(uuid, username, userRole, isSuperAdmin);
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof SecureUserOrganizationAwareResponseModel)) return false;

        final SecureUserOrganizationAwareResponseModel that = (SecureUserOrganizationAwareResponseModel) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }
}