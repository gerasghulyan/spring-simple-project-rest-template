package com.vntana.core.service.token.reset.password.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:15 PM
 */
public class CreateTokenResetPasswordDto implements ServiceDto {

    private final String token;
    private final String userUuid;
    private final LocalDateTime expirationDate;

    public CreateTokenResetPasswordDto(final String token, final String userUuid, final LocalDateTime expirationDate) {
        Assert.hasText(token, "The token should not be null or empty");
        Assert.hasText(userUuid, "The userUuid should not be null or empty");
        Assert.notNull(expirationDate, "The expirationDate should not be null or empty");
        this.token = token;
        this.userUuid = userUuid;
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateTokenResetPasswordDto)) {
            return false;
        }
        final CreateTokenResetPasswordDto that = (CreateTokenResetPasswordDto) o;
        return new EqualsBuilder()
                .append(token, that.token)
                .append(userUuid, that.userUuid)
                .append(expirationDate, that.expirationDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(token)
                .append(userUuid)
                .append(expirationDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", token)
                .append("userUuid", userUuid)
                .append("expirationDate", expirationDate)
                .toString();
    }

    public String getToken() {
        return token;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}
