package com.vntana.core.service.token.auth.dto;

import com.vntana.commons.service.dto.ServiceDto;
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
public class CreateTokenAuthenticationDto implements ServiceDto {

    private final String userUuid;
    private final String token;
    private final LocalDateTime expiration;

    public CreateTokenAuthenticationDto(final String userUuid, final String token, final LocalDateTime expiration) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        Assert.hasText(token, "The token should not be null or empty");
        Assert.notNull(expiration, "The expiration should not be null or empty");
        this.userUuid = userUuid;
        this.token = token;
        this.expiration = expiration;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateTokenAuthenticationDto)) {
            return false;
        }
        final CreateTokenAuthenticationDto that = (CreateTokenAuthenticationDto) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(token, that.token)
                .append(expiration, that.expiration)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(token)
                .append(expiration)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userUuid", userUuid)
                .append("expiration", expiration)
                .toString();
    }
}
