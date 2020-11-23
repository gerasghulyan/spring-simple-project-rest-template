package com.vntana.core.service.token.invitation.user.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 1:46 PM
 */
public class InvitationUuidAndTokenDto implements ServiceDto {

    private final String invitationUuid;
    
    private final String token;

    public InvitationUuidAndTokenDto(final String invitationUuid, final String token) {
        Assert.hasText(invitationUuid, "The invitationUuid should not be null or empty");
        Assert.hasText(token, "The token should not be null or empty");
        this.invitationUuid = invitationUuid;
        this.token = token;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvitationUuidAndTokenDto)) {
            return false;
        }
        final InvitationUuidAndTokenDto that = (InvitationUuidAndTokenDto) o;
        return new EqualsBuilder()
                .append(invitationUuid, that.invitationUuid)
                .append(token, that.token)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(invitationUuid)
                .append(token)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("invitationUuid", invitationUuid)
                .toString();
    }

    public String getInvitationUuid() {
        return invitationUuid;
    }

    public String getToken() {
        return token;
    }
}
