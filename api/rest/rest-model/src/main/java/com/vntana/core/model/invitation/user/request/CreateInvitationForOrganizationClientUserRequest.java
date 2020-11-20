package com.vntana.core.model.invitation.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

import static com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel.*;

/**
 * Created by Diana Gevorgyan
 * Date: 11/10/20
 * Time: 8:43 AM
 */
public class CreateInvitationForOrganizationClientUserRequest extends AbstractRequestModel implements ValidatableRequest<InvitationUserErrorResponseModel> {

    @JsonProperty("invitations")
    private List<SingleUserInvitationToClient> invitations;

    @JsonProperty("email")
    private String email;

    @JsonProperty("inviterUserUuid")
    private String inviterUserUuid;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public CreateInvitationForOrganizationClientUserRequest() {
        super();
    }

    public CreateInvitationForOrganizationClientUserRequest(
            final List<SingleUserInvitationToClient> invitations,
            final String email,
            final String inviterUserUuid,
            final String organizationUuid) {
        this.invitations = invitations;
        this.email = email;
        this.inviterUserUuid = inviterUserUuid;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public List<InvitationUserErrorResponseModel> validate() {
        if (CollectionUtils.isEmpty(invitations)) {
            return Collections.singletonList(MISSING_INVITATIONS);
        }
        if (StringUtils.isEmpty(email)) {
            return Collections.singletonList(MISSING_INVITED_USER_EMAIL);
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
        if (!(o instanceof CreateInvitationForOrganizationClientUserRequest)) {
            return false;
        }
        final CreateInvitationForOrganizationClientUserRequest that = (CreateInvitationForOrganizationClientUserRequest) o;
        return new EqualsBuilder()
                .append(invitations, that.invitations)
                .append(email, that.email)
                .append(inviterUserUuid, that.inviterUserUuid)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(invitations)
                .append(email)
                .append(inviterUserUuid)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userRoles", invitations)
                .append("email", email)
                .append("inviterUserUuid", inviterUserUuid)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public List<SingleUserInvitationToClient> getInvitations() {
        return invitations;
    }

    public String getEmail() {
        return email;
    }

    public String getInviterUserUuid() {
        return inviterUserUuid;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}
