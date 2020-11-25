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

import static com.vntana.core.model.utils.EmailSanitizerUtility.sanitize;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 12:46 PM
 */
public class SendUserResetPasswordRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("email")
    private String email;

    @JsonProperty("token")
    private String token;

    public SendUserResetPasswordRequest() {
        super();
    }

    public SendUserResetPasswordRequest(final String email, final String token) {
        super();
        this.email = sanitize(email);
        this.token = token;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        if (StringUtils.isEmpty(email)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_EMAIL);
        }
        if (StringUtils.isEmpty(token)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_RESET_PASSWORD_TOKEN);
        }
        return Collections.emptyList();
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SendUserResetPasswordRequest)) {
            return false;
        }
        final SendUserResetPasswordRequest that = (SendUserResetPasswordRequest) o;
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
                .toString();
    }
}
