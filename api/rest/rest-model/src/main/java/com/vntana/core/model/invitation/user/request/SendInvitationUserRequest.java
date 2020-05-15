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

import static com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel.*;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 10:51 AM
 */
public class SendInvitationUserRequest extends AbstractRequestModel implements ValidatableRequest<InvitationUserErrorResponseModel> {

    @JsonProperty("email")
    private String email;

    @JsonProperty("token")
    private String token;

    @JsonProperty("inviterUserUuid")
    private String inviterUserUuid;

    @JsonProperty("organizationName")
    private String organizationName;

    public SendInvitationUserRequest() {
        super();
    }

    public SendInvitationUserRequest(final String email, final String token, final String inviterUserUuid, final String organizationName) {
        this.email = email;
        this.token = token;
        this.inviterUserUuid = inviterUserUuid;
        this.organizationName = organizationName;
    }

    @Override
    public List<InvitationUserErrorResponseModel> validate() {
        if (StringUtils.isEmpty(email)) {
            return Collections.singletonList(MISSING_INVITED_USER_EMAIL);
        }
        if (StringUtils.isEmpty(token)) {
            return Collections.singletonList(MISSING_INVITATION_TOKEN);
        }
        if (StringUtils.isEmpty(inviterUserUuid)) {
            return Collections.singletonList(MISSING_INVITER_USER_UUID);
        }
        if (StringUtils.isEmpty(organizationName)) {
            return Collections.singletonList(MISSING_INVITING_ORGANIZATION_NAME);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof SendInvitationUserRequest)) return false;

        final SendInvitationUserRequest that = (SendInvitationUserRequest) o;

        return new EqualsBuilder()
                .append(email, that.email)
                .append(token, that.token)
                .append(inviterUserUuid, that.inviterUserUuid)
                .append(organizationName, that.organizationName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .append(token)
                .append(inviterUserUuid)
                .append(organizationName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("inviterUserUuid", inviterUserUuid)
                .append("organizationName", organizationName)
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getInviterUserUuid() {
        return inviterUserUuid;
    }

    public String getOrganizationName() {
        return organizationName;
    }
}