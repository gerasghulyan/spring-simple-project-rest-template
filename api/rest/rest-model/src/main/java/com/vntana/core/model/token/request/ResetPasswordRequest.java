package com.vntana.core.model.token.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:18 PM
 */
public class ResetPasswordRequest extends AbstractRequestModel implements ValidatableRequest<TokenErrorResponseModel> {

    @JsonProperty("email")
    private String email;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(final String email) {
        this.email = email;
    }

    @Override
    public List<TokenErrorResponseModel> validate() {
        final List<TokenErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(email)) {
            errors.add(TokenErrorResponseModel.MISSING_EMAIL);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResetPasswordRequest)) {
            return false;
        }
        final ResetPasswordRequest that = (ResetPasswordRequest) o;
        return new EqualsBuilder()
                .append(email, that.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
