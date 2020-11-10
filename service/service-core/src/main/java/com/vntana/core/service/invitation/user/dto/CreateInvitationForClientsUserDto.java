package com.vntana.core.service.invitation.user.dto;

import com.vntana.commons.service.dto.ServiceDto;
import com.vntana.core.domain.user.UserRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Created by Diana Gevorgyan
 * Date: 11/10/20
 * Time: 11:53 AM
 */
public class CreateInvitationForClientsUserDto implements ServiceDto {

    private final Map<String, UserRole> userRoles;
    private final String email;
    private final String inviterUserUuid;

    public CreateInvitationForClientsUserDto(
            final Map<String, UserRole> userRoles,
            final String email,
            final String inviterUserUuid) {
        Assert.notNull(userRoles, "The user roles should not be null");
        Assert.hasText(email, "The email should not be null or empty");
        Assert.hasText(inviterUserUuid, "The inviterUserUuid should not be null or empty");
        this.userRoles = userRoles;
        this.email = email;
        this.inviterUserUuid = inviterUserUuid;
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
                .append(userRoles, that.userRoles)
                .append(email, that.email)
                .append(inviterUserUuid, that.inviterUserUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userRoles)
                .append(email)
                .append(inviterUserUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userRoles", userRoles)
                .append("email", email)
                .append("inviterUserUuid", inviterUserUuid)
                .toString();
    }

    public Map<String, UserRole> getUserRoles() {
        return userRoles;
    }

    public String getEmail() {
        return email;
    }

    public String getInviterUserUuid() {
        return inviterUserUuid;
    }
}
