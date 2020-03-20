package com.vntana.core.service.token.auth.dto;

import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 2:25 PM
 */
public final class AuthTokenCreateDto implements ServiceDto {

    private final String userUuid;
    private final String token;

    public AuthTokenCreateDto(final String userUuid, final String token) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        Assert.hasText(token, "The token should not be null or empty");
        this.userUuid = userUuid;
        this.token = token;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthTokenCreateDto)) {
            return false;
        }
        final AuthTokenCreateDto that = (AuthTokenCreateDto) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(token, that.token)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(token)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userUuid", userUuid)
                .toString();
    }
}
