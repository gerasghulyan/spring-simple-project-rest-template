package com.vntana.core.model.token.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:35 AM
 */
public class TokenAuthenticationPersistWithClientOrganizationRequest extends TokenAuthenticationPersistRequest implements ValidatableRequest<TokenAuthenticationErrorResponseModel> {

    @JsonProperty("clientUuid")
    private String clientUuid;

    public TokenAuthenticationPersistWithClientOrganizationRequest() {
        super();
    }

    public TokenAuthenticationPersistWithClientOrganizationRequest(final String userUuid, final String token, final LocalDateTime expiration, final String clientUuid) {
        super(userUuid, token, expiration);
        this.clientUuid = clientUuid;
    }

    @Override
    public List<TokenAuthenticationErrorResponseModel> validate() {
        final List<TokenAuthenticationErrorResponseModel> errors = super.validate();
        if (StringUtils.isEmpty(clientUuid)) {
            errors.add(TokenAuthenticationErrorResponseModel.MISSING_CLIENT_ORGANIZATION);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof TokenAuthenticationPersistWithClientOrganizationRequest)) return false;

        final TokenAuthenticationPersistWithClientOrganizationRequest that = (TokenAuthenticationPersistWithClientOrganizationRequest) o;

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

    public String getClientUuid() {
        return clientUuid;
    }
}