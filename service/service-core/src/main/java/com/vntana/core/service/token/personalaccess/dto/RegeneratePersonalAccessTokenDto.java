package com.vntana.core.service.token.personalaccess.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 9:39 AM
 */
public class RegeneratePersonalAccessTokenDto implements ServiceDto {

    private final String userUuid;
    private final String token;

    public RegeneratePersonalAccessTokenDto(final String userUuid, final String token) {
        this.userUuid = userUuid;
        this.token = token;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegeneratePersonalAccessTokenDto)) {
            return false;
        }
        final RegeneratePersonalAccessTokenDto that = (RegeneratePersonalAccessTokenDto) o;
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
                .appendSuper(super.toString())
                .append("userUuid", userUuid)
                .append("token", token)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getToken() {
        return token;
    }
}
