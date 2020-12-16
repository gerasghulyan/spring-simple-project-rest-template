package com.vntana.core.model.user.role.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Vardan Aivazian
 * Date: 10.12.2020
 * Time: 18:24
 */
public class UpdateClientRoleRequest extends AbstractRequestModel {
    
    @JsonProperty("clientUuid")
    private String clientUuid;

    @JsonProperty("clientRole")
    private UserRoleModel clientRole;

    public UpdateClientRoleRequest() {
        super();
    }

    public UpdateClientRoleRequest(final String clientUuid, final UserRoleModel clientRole) {
        this.clientUuid = clientUuid;
        this.clientRole = clientRole;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpdateClientRoleRequest)) {
            return false;
        }
        final UpdateClientRoleRequest that = (UpdateClientRoleRequest) o;
        return new EqualsBuilder()
                .append(clientUuid, that.clientUuid)
                .append(clientRole, that.clientRole)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(clientUuid)
                .append(clientRole)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("clientUuid", clientUuid)
                .append("clientRole", clientRole)
                .toString();
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(final String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public UserRoleModel getClientRole() {
        return clientRole;
    }

    public void setClientRole(final UserRoleModel clientRole) {
        this.clientRole = clientRole;
    }
}
