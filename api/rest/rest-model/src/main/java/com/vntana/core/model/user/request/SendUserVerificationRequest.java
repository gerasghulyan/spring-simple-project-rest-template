package com.vntana.core.model.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/12/19
 * Time: 1:29 PM
 */
public class SendUserVerificationRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("email")
    private String email;

    @JsonProperty("token")
    private String token;

    public SendUserVerificationRequest() {
        super();
    }

    public SendUserVerificationRequest(final String email, final String token) {
        this.email = email;
        this.token = token;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        final List<UserErrorResponseModel> errors = new LinkedList<>();
        if (StringUtils.isBlank(getEmail())) {
            errors.add(UserErrorResponseModel.MISSING_EMAIL);
        }
        if (StringUtils.isBlank(token)) {
            errors.add(UserErrorResponseModel.MISSING_VERIFICATION_TOKEN);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SendUserVerificationRequest)) {
            return false;
        }
        final SendUserVerificationRequest that = (SendUserVerificationRequest) o;
        return new EqualsBuilder()
                .append(email, that.email)
                .append(token, that.token)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .append(token)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("token", token)
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
