package com.vntana.core.model.invitation.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 5:31 PM
 */
public class AcceptInvitationUserRequest extends AbstractRequestModel implements ValidatableRequest<InvitationUserErrorResponseModel> {

    @JsonProperty("token")
    private String token;

    public AcceptInvitationUserRequest() {
        super();
    }

    public AcceptInvitationUserRequest(final String token) {
        this.token = token;
    }

    @Override
    public List<InvitationUserErrorResponseModel> validate() {
        if (StringUtils.isEmpty(token)) {
            return Collections.singletonList(InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcceptInvitationUserRequest)) {
            return false;
        }
        final AcceptInvitationUserRequest that = (AcceptInvitationUserRequest) o;
        return new EqualsBuilder()
                .append(token, that.token)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(token)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", token)
                .toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
