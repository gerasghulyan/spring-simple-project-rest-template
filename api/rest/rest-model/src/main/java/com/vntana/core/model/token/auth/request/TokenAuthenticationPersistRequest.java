package com.vntana.core.model.token.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:35 AM
 */
public class TokenAuthenticationPersistRequest extends AbstractRequestModel implements ValidatableRequest<TokenAuthenticationErrorResponseModel> {

    @JsonProperty("userUuid")
    private String userUuid;

    @JsonProperty("token")
    private String token;

    @JsonProperty("expiration")
    private LocalDateTime expiration;

    public TokenAuthenticationPersistRequest() {
        super();
    }

    public TokenAuthenticationPersistRequest(final String userUuid, final String token, final LocalDateTime expiration) {
        super();
        this.userUuid = userUuid;
        this.token = token;
        this.expiration = expiration;
    }

    @Override
    public List<TokenAuthenticationErrorResponseModel> validate() {
        final List<TokenAuthenticationErrorResponseModel> errors = new LinkedList<>();
        if (StringUtils.isEmpty(userUuid)) {
            errors.add(TokenAuthenticationErrorResponseModel.MISSING_USER_UUID);
        }
        if (StringUtils.isEmpty(token)) {
            errors.add(TokenAuthenticationErrorResponseModel.MISSING_TOKEN);
        }
        if (Objects.isNull(expiration)) {
            errors.add(TokenAuthenticationErrorResponseModel.MISSING_EXPIRATION);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenAuthenticationPersistRequest)) {
            return false;
        }
        final TokenAuthenticationPersistRequest that = (TokenAuthenticationPersistRequest) o;
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

    public String getUserUuid() {
        return userUuid;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }
}
