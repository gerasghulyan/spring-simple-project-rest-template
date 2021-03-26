package com.vntana.core.service.token.personalaccess.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Diana Gevorgyan
 * Date: 3/23/21
 * Time: 10:42 AM
 */
public class CreatePersonalAccessTokenDto implements ServiceDto {

    private final String userUuid;
    private final String token;

    public CreatePersonalAccessTokenDto(final String userUuid, final String token) {
        Assert.hasText(userUuid, "The userUuid should not be null or empty");
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
        if (!(o instanceof CreatePersonalAccessTokenDto)) {
            return false;
        }
        final CreatePersonalAccessTokenDto that = (CreatePersonalAccessTokenDto) o;
        return new EqualsBuilder()
                .append(getUserUuid(), that.getUserUuid())
                .append(getToken(), that.getToken())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getUserUuid())
                .append(getToken())
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
}
