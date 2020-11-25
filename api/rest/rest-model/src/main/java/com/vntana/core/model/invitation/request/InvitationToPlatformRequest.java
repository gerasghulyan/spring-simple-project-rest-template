package com.vntana.core.model.invitation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.invitation.error.InvitationErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

import static com.vntana.core.model.utils.EmailSanitizerUtility.sanitize;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/25/19
 * Time: 5:29 PM
 */
public class InvitationToPlatformRequest extends AbstractRequestModel implements ValidatableRequest<InvitationErrorResponseModel> {

    @JsonProperty("token")
    private String token;

    @JsonProperty("email")
    private String email;

    public InvitationToPlatformRequest() {
        super();
    }

    public InvitationToPlatformRequest(final String token, final String email) {
        super();
        this.token = token;
        this.email = sanitize(email);
    }

    @Override
    public List<InvitationErrorResponseModel> validate() {
        if (StringUtils.isEmpty(token)) {
            return Collections.singletonList(InvitationErrorResponseModel.MISSING_TOKEN);
        }
        if (StringUtils.isEmpty(email)) {
            return Collections.singletonList(InvitationErrorResponseModel.MISSING_EMAIL);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvitationToPlatformRequest)) {
            return false;
        }
        final InvitationToPlatformRequest that = (InvitationToPlatformRequest) o;
        return new EqualsBuilder()
                .append(token, that.token)
                .append(email, that.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(token)
                .append(email)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", token)
                .append("email", email)
                .toString();
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }
}
