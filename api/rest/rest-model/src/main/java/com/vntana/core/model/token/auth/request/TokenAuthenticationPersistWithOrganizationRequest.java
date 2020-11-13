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
public class TokenAuthenticationPersistWithOrganizationRequest extends TokenAuthenticationPersistRequest implements ValidatableRequest<TokenAuthenticationErrorResponseModel> {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public TokenAuthenticationPersistWithOrganizationRequest() {
        super();
    }

    public TokenAuthenticationPersistWithOrganizationRequest(final String userUuid, final String token, final LocalDateTime expiration, final String organizationUuid) {
        super(userUuid, token, expiration);
        this.organizationUuid = organizationUuid;
    }

    @Override
    public List<TokenAuthenticationErrorResponseModel> validate() {
        final List<TokenAuthenticationErrorResponseModel> errors = super.validate();
        if (StringUtils.isEmpty(organizationUuid)) {
            errors.add(TokenAuthenticationErrorResponseModel.MISSING_ORGANIZATION);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof TokenAuthenticationPersistWithOrganizationRequest)) return false;

        final TokenAuthenticationPersistWithOrganizationRequest that = (TokenAuthenticationPersistWithOrganizationRequest) o;

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

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}
