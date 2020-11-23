package com.vntana.core.service.token.invitation.user.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:15 PM
 */
public class CreateInvitationUserToOrganizationDto implements ServiceDto {

    private final String token;
    private final String invitationUserUuid;

    public CreateInvitationUserToOrganizationDto(final String token, final String invitationUserUuid) {
        Assert.hasText(token, "The token should not be null or empty");
        Assert.hasText(invitationUserUuid, "The invitationUserUuid should not be null or empty");
        this.token = token;
        this.invitationUserUuid = invitationUserUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateInvitationUserToOrganizationDto)) {
            return false;
        }
        final CreateInvitationUserToOrganizationDto that = (CreateInvitationUserToOrganizationDto) o;
        return new EqualsBuilder()
                .append(token, that.token)
                .append(invitationUserUuid, that.invitationUserUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(token)
                .append(invitationUserUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("invitationUserUuid", invitationUserUuid)
                .toString();
    }

    public String getToken() {
        return token;
    }

    public String getInvitationUserUuid() {
        return invitationUserUuid;
    }
}
