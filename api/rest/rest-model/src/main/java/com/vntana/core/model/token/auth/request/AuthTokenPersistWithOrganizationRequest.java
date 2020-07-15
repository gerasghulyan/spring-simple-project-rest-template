package com.vntana.core.model.token.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel;
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
public class AuthTokenPersistWithOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<AuthTokenErrorResponseModel> {

    @JsonProperty("userUuid")
    private String userUuid;

    @JsonProperty("token")
    private String token;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("expiration")
    private LocalDateTime expiration;

    public AuthTokenPersistWithOrganizationRequest() {
        super();
    }

    public AuthTokenPersistWithOrganizationRequest(final String userUuid, final String token, final String organizationUuid, final LocalDateTime expiration) {
        super();
        this.userUuid = userUuid;
        this.token = token;
        this.organizationUuid = organizationUuid;
        this.expiration = expiration;
    }

    @Override
    public List<AuthTokenErrorResponseModel> validate() {
        final List<AuthTokenErrorResponseModel> errors = new LinkedList<>();
        if (StringUtils.isEmpty(userUuid)) {
            errors.add(AuthTokenErrorResponseModel.MISSING_USER_UUID);
        }
        if (StringUtils.isEmpty(token)) {
            errors.add(AuthTokenErrorResponseModel.MISSING_TOKEN);
        }
        if (StringUtils.isEmpty(organizationUuid)) {
            errors.add(AuthTokenErrorResponseModel.MISSING_ORGANIZATION);
        }
        if (Objects.isNull(expiration)) {
            errors.add(AuthTokenErrorResponseModel.MISSING_EXPIRATION);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthTokenPersistWithOrganizationRequest)) {
            return false;
        }
        final AuthTokenPersistWithOrganizationRequest that = (AuthTokenPersistWithOrganizationRequest) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(token, that.token)
                .append(organizationUuid, that.organizationUuid)
                .append(expiration, that.expiration)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(token)
                .append(organizationUuid)
                .append(expiration)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userUuid", userUuid)
                .append("organizationUuid", organizationUuid)
                .append("expiration", expiration)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getToken() {
        return token;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }
}
