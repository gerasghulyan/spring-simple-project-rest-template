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
public final class CreateTokenAuthenticationWithOrganizationDto extends CreateTokenAuthenticationDto {

    private final String organizationUuid;

    public CreateTokenAuthenticationWithOrganizationDto(final String userUuid, final String token, final LocalDateTime expiration, final String organizationUuid) {
        super(userUuid, token, expiration);
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
        this.organizationUuid = organizationUuid;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof CreateTokenAuthenticationWithOrganizationDto)) return false;

        final CreateTokenAuthenticationWithOrganizationDto that = (CreateTokenAuthenticationWithOrganizationDto) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("organizationUuid", organizationUuid)
                .toString();
    }
}
