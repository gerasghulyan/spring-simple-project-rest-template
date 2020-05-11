package com.vntana.core.service.invitation.user.dto;

import com.vntana.core.domain.user.UserRole;
import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:07 PM
 */
public class CreateInvitationUserDto implements ServiceDto {

    private final UserRole userRole;
    private final String email;
    private final String inviterUserUuid;

    public CreateInvitationUserDto(final UserRole userRole, final String email, final String inviterUserUuid) {
        Assert.notNull(userRole, "The userRole should nto be null");
        Assert.hasText(email, "The email should not be null or empty");
        Assert.hasText(inviterUserUuid, "The inviterUserUuid should not be null or empty");
        this.userRole = userRole;
        this.email = email;
        this.inviterUserUuid = inviterUserUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof CreateInvitationUserDto)) return false;

        final CreateInvitationUserDto that = (CreateInvitationUserDto) o;

        return new EqualsBuilder()
                .append(userRole, that.userRole)
                .append(email, that.email)
                .append(inviterUserUuid, that.inviterUserUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userRole)
                .append(email)
                .append(inviterUserUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userRole", userRole)
                .append("email", email)
                .append("inviterUserUuid", inviterUserUuid)
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
}
