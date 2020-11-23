package com.vntana.core.service.token.invitation.user.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 1:44 PM
 */
public class CreateInvitationUserToClientDto implements ServiceDto {

    private final List<InvitationUuidAndTokenDto> invitationUuidAndTokens;

    public CreateInvitationUserToClientDto(final List<InvitationUuidAndTokenDto> invitationUuidAndToken) {
        Assert.isTrue(!CollectionUtils.isEmpty(invitationUuidAndToken), "The invitationUuidAndTokens should not be null or empty");
        this.invitationUuidAndTokens = invitationUuidAndToken;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateInvitationUserToClientDto)) {
            return false;
        }
        final CreateInvitationUserToClientDto that = (CreateInvitationUserToClientDto) o;
        return new EqualsBuilder()
                .append(invitationUuidAndTokens, that.invitationUuidAndTokens)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(invitationUuidAndTokens)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("invitationUuidAndToken", invitationUuidAndTokens.stream().map(InvitationUuidAndTokenDto::getInvitationUuid).toArray())
                .toString();
    }

    public List<InvitationUuidAndTokenDto> getInvitationUuidAndTokens() {
        return invitationUuidAndTokens;
    }
}
