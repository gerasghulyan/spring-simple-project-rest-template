package com.vntana.core.model.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:06 PM
 */
public class FindUserByEmailRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("email")
    private String email;

    public FindUserByEmailRequest() {
    }

    public FindUserByEmailRequest(final String email) {
        this.email = email;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        final List<UserErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(email)) {
            errors.add(UserErrorResponseModel.MISSING_EMAIL);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FindUserByEmailRequest)) {
            return false;
        }
        final FindUserByEmailRequest that = (FindUserByEmailRequest) o;
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
