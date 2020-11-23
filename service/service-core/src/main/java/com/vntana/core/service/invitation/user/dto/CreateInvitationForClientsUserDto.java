package com.vntana.core.service.invitation.user.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/10/20
 * Time: 11:53 AM
 */
public class CreateInvitationForClientsUserDto implements ServiceDto {

    private final List<UserInvitationToClient> invitations;
    
    private final String email;
    
    private final String inviterUserUuid;
    
    private final String organizationUuid;

    public CreateInvitationForClientsUserDto(
            final List<UserInvitationToClient> invitations,
            final String email,
            final String inviterUserUuid, 
            final String organizationUuid) {
        Assert.notNull(invitations, "The invitations should not be null or empty");
        Assert.hasText(email, "The email should not be null or empty");
        Assert.hasText(inviterUserUuid, "The inviterUserUuid should not be null or empty");
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
        this.invitations = invitations;
        this.email = email;
        this.inviterUserUuid = inviterUserUuid;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateInvitationForClientsUserDto)) {
            return false;
        }
        final CreateInvitationForClientsUserDto that = (CreateInvitationForClientsUserDto) o;
        return new EqualsBuilder()
                .append(invitations, that.invitations)
                .append(email, that.email)
                .append(inviterUserUuid, that.inviterUserUuid)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(invitations)
                .append(email)
                .append(inviterUserUuid)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("invitations", invitations)
                .append("email", email)
                .append("inviterUserUuid", inviterUserUuid)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public List<UserInvitationToClient> getInvitations() {
        return invitations;
    }

    public String getEmail() {
        return email;
    }

    public String getInviterUserUuid() {
        return inviterUserUuid;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}
