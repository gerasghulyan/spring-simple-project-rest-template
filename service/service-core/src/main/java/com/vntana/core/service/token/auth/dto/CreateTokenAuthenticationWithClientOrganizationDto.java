package com.vntana.core.service.token.auth.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 2:25 PM
 */
public final class CreateTokenAuthenticationWithClientOrganizationDto extends CreateTokenAuthenticationDto {

    private final String clientUuid;

    public CreateTokenAuthenticationWithClientOrganizationDto(final String userUuid, final String token, final LocalDateTime expiration, final String clientUuid) {
        super(userUuid, token, expiration);
        Assert.hasText(clientUuid, "The clientUuid should not be null or empty");
        this.clientUuid = clientUuid;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof CreateTokenAuthenticationWithClientOrganizationDto)) return false;

        final CreateTokenAuthenticationWithClientOrganizationDto that = (CreateTokenAuthenticationWithClientOrganizationDto) o;

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
}