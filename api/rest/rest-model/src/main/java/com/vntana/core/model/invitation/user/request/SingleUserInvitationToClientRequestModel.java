package com.vntana.core.model.invitation.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 11/20/20
 * Time: 4:36 PM
 */
public class SingleUserInvitationToClientRequestModel extends AbstractRequestModel {

    @JsonProperty("clientUuid")
    private String clientUuid;

    @JsonProperty("role")
    private UserRoleModel role;

    public SingleUserInvitationToClientRequestModel() {
    }

    public SingleUserInvitationToClientRequestModel(final String clientUuid, final UserRoleModel role) {
        this.clientUuid = clientUuid;
        this.role = role;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SingleUserInvitationToClientRequestModel)) {
            return false;
        }
        final SingleUserInvitationToClientRequestModel that = (SingleUserInvitationToClientRequestModel) o;
        return new EqualsBuilder()
                .append(clientUuid, that.clientUuid)
                .append(role, that.role)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(clientUuid)
                .append(role)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("clientUuid", clientUuid)
                .append("role", role)
                .toString();
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public UserRoleModel getRole() {
        return role;
    }
}
