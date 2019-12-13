package com.vntana.core.model.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 5:30 PM
 */
public class ResetUserPasswordRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    private ResetUserPasswordRequest() {
        super();
    }

    public ResetUserPasswordRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        if (StringUtils.isEmpty(email)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_EMAIL);
        }
        if (StringUtils.isEmpty(password)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_PASSWORD);
        }
        return Collections.emptyList();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResetUserPasswordRequest)) {
            return false;
        }
        final ResetUserPasswordRequest that = (ResetUserPasswordRequest) o;
        return new EqualsBuilder()
                .append(email, that.email)
                .append(password, that.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .append(password)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("password", password)
                .toString();
    }
}
