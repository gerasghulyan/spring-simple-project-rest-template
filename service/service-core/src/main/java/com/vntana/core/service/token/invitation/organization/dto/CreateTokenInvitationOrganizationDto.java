package com.vntana.core.service.token.invitation.organization.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 10:33 AM
 */
public class CreateTokenInvitationOrganizationDto implements ServiceDto {

    private final String token;
    private final String invitationOrganizationUuid;

    public CreateTokenInvitationOrganizationDto(final String token, final String invitationOrganizationUuid) {
        Assert.hasText(token, "The token should not be null or empty");
        Assert.hasText(invitationOrganizationUuid, "The invitationOrganizationUuid should not be null or empty");
        this.token = token;
        this.invitationOrganizationUuid = invitationOrganizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateTokenInvitationOrganizationDto)) {
            return false;
        }
        final CreateTokenInvitationOrganizationDto that = (CreateTokenInvitationOrganizationDto) o;
        return new EqualsBuilder()
                .append(token, that.token)
                .append(invitationOrganizationUuid, that.invitationOrganizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(token)
                .append(invitationOrganizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", token)
                .append("invitationOrganizationUuid", invitationOrganizationUuid)
                .toString();
    }

    public String getToken() {
        return token;
    }

    public String getInvitationOrganizationUuid() {
        return invitationOrganizationUuid;
    }
}
