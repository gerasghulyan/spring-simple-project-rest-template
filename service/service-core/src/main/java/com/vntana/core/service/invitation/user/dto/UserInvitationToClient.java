package com.vntana.core.service.invitation.user.dto;

import com.vntana.commons.service.dto.ServiceDto;
import com.vntana.core.domain.user.UserRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 11/23/20
 * Time: 3:26 PM
 */
public class UserInvitationToClient implements ServiceDto {

    private final String clientUuid;
    
    private final UserRole role;

    public UserInvitationToClient(final String clientUuid, final UserRole role) {
        this.clientUuid = clientUuid;
        this.role = role;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInvitationToClient)) {
            return false;
        }
        final UserInvitationToClient that = (UserInvitationToClient) o;
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

    public UserRole getRole() {
        return role;
    }
}
