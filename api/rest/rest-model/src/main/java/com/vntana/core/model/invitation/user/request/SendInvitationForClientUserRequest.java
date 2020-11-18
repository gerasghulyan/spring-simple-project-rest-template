package com.vntana.core.model.invitation.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel.*;

/**
 * Created by Diana Gevorgyan
 * Date: 11/18/20
 * Time: 2:40 PM
 */
public class SendInvitationForClientUserRequest extends AbstractRequestModel implements ValidatableRequest<InvitationUserErrorResponseModel> {

    @JsonProperty("email")
    private String email;

    @JsonProperty("invitationTokens")
    private Map<String, String> invitationTokens;

    @JsonProperty("inviterUserUuid")
    private String inviterUserUuid;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public SendInvitationForClientUserRequest() {
    }

    public SendInvitationForClientUserRequest(
            final String email,
            final Map<String, String> invitationTokens,
            final String inviterUserUuid,
            final String organizationUuid) {
        this.email = email;
        this.invitationTokens = invitationTokens;
        this.inviterUserUuid = inviterUserUuid;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public List<InvitationUserErrorResponseModel> validate() {
        if (StringUtils.isEmpty(email)) {
            return Collections.singletonList(MISSING_INVITED_USER_EMAIL);
        }
        if (MapUtils.isEmpty(invitationTokens)) {
            return Collections.singletonList(MISSING_INVITATION_TOKENS);
        }
        if (StringUtils.isEmpty(inviterUserUuid)) {
            return Collections.singletonList(MISSING_INVITER_USER_UUID);
        }
        if (StringUtils.isEmpty(organizationUuid)) {
            return Collections.singletonList(MISSING_INVITING_ORGANIZATION_UUID);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SendInvitationForClientUserRequest)) {
            return false;
        }
        final SendInvitationForClientUserRequest that = (SendInvitationForClientUserRequest) o;
        return new EqualsBuilder()
                .append(email, that.email)
                .append(invitationTokens, that.invitationTokens)
                .append(inviterUserUuid, that.inviterUserUuid)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .append(invitationTokens)
                .append(inviterUserUuid)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("email", email)
                .append("invitationTokens", invitationTokens)
                .append("inviterUserUuid", inviterUserUuid)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public Map<String, String> getInvitationTokens() {
        return invitationTokens;
    }

    public String getInviterUserUuid() {
        return inviterUserUuid;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}
