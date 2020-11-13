package com.vntana.core.service.invitation.user.dto;

import com.vntana.commons.service.dto.ServiceDto;
import com.vntana.core.domain.user.UserRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:07 PM
 */
public class CreateInvitationForOrganizationUserDto implements ServiceDto {

    private final UserRole userRole;
    private final String email;
    private final String inviterUserUuid;
    private final String organizationUuid;

    public CreateInvitationForOrganizationUserDto(final UserRole userRole, final String email, final String inviterUserUuid, final String organizationUuid) {
        Assert.notNull(userRole, "The userRole should nto be null");
        Assert.hasText(email, "The email should not be null or empty");
        Assert.hasText(inviterUserUuid, "The inviterUserUuid should not be null or empty");
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
        this.userRole = userRole;
        this.email = email;
        this.inviterUserUuid = inviterUserUuid;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof CreateInvitationForOrganizationUserDto)) return false;

        final CreateInvitationForOrganizationUserDto that = (CreateInvitationForOrganizationUserDto) o;

        return new EqualsBuilder()
                .append(userRole, that.userRole)
                .append(email, that.email)
                .append(inviterUserUuid, that.inviterUserUuid)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userRole)
                .append(email)
                .append(inviterUserUuid)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userRole", userRole)
                .append("email", email)
                .append("inviterUserUuid", inviterUserUuid)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public UserRole getUserRole() {
        return userRole;
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
