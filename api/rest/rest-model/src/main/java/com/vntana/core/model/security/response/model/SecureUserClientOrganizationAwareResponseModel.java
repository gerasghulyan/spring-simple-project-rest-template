package com.vntana.core.model.security.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/12/2020
 * Time: 11:04 AM
 */
public class SecureUserClientOrganizationAwareResponseModel extends SecureUserOrganizationAwareResponseModel {

    @JsonProperty("clientUuid")
    private String clientUuid;

    public SecureUserClientOrganizationAwareResponseModel() {
        super();
    }

    public SecureUserClientOrganizationAwareResponseModel(final String uuid,
                                                          final String username,
                                                          final UserRoleModel userRole,
                                                          final Boolean isSuperAdmin,
                                                          final String organizationUuid,
                                                          final String clientUuid) {
        super(uuid, username, userRole, isSuperAdmin, organizationUuid);
        this.clientUuid = clientUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof SecureUserClientOrganizationAwareResponseModel)) return false;

        final SecureUserClientOrganizationAwareResponseModel that = (SecureUserClientOrganizationAwareResponseModel) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(clientUuid, that.clientUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(clientUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("clientUuid", clientUuid)
                .toString();
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(final String clientUuid) {
        this.clientUuid = clientUuid;
    }
}