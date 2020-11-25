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
import static com.vntana.core.model.utils.EmailSanitizerUtility.sanitize;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 10:51 AM
 */
public class SendInvitationForOrganizationUserRequest extends AbstractRequestModel implements ValidatableRequest<InvitationUserErrorResponseModel> {

    @JsonProperty("email")
    private String email;

    @JsonProperty("token")
    private String token;

    @JsonProperty("inviterUserUuid")
    private String inviterUserUuid;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public SendInvitationForOrganizationUserRequest() {
        super();
    }

    public SendInvitationForOrganizationUserRequest(final String email, final String token, final String inviterUserUuid, final String organizationUuid) {
        this.email = sanitize(email);
        this.token = token;
        this.inviterUserUuid = inviterUserUuid;
        this.organizationUuid = organizationUuid;
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
        if (StringUtils.isEmpty(organizationUuid)) {
            return Collections.singletonList(MISSING_INVITING_ORGANIZATION_UUID);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof SendInvitationForOrganizationUserRequest)) return false;

        final SendInvitationForOrganizationUserRequest that = (SendInvitationForOrganizationUserRequest) o;

        return new EqualsBuilder()
                .append(email, that.email)
                .append(token, that.token)
                .append(inviterUserUuid, that.inviterUserUuid)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .append(token)
                .append(inviterUserUuid)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("inviterUserUuid", inviterUserUuid)
                .append("organizationUuid", organizationUuid)
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

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}