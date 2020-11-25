package com.vntana.core.model.invitation.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 11/24/20
 * Time: 4:37 PM
 */
public class AcceptInvitationUserToClientResponseModel extends AbstractAcceptUserInvitationResponseModel {

    @JsonProperty("clientUuid")
    private String clientUuid;

    public AcceptInvitationUserToClientResponseModel() {
        super();
    }

    public AcceptInvitationUserToClientResponseModel(
            final String clientUuid,
            final String userUuid,
            final UserRoleModel userRoleModel) {
        super(userUuid, userRoleModel);
        this.clientUuid = clientUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcceptInvitationUserToClientResponseModel)) {
            return false;
        }
        final AcceptInvitationUserToClientResponseModel that = (AcceptInvitationUserToClientResponseModel) o;
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
