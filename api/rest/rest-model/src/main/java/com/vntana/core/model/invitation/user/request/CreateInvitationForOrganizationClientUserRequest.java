package com.vntana.core.model.invitation.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.auth.response.UserRoleModel;
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
 * Date: 11/10/20
 * Time: 8:43 AM
 */
public class CreateInvitationForOrganizationClientUserRequest extends AbstractRequestModel implements ValidatableRequest<InvitationUserErrorResponseModel> {

    @JsonProperty("userRoles")
    private final Map<String, UserRoleModel> userRoles;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("inviterUserUuid")
    private final String inviterUserUuid;

    @JsonProperty("organizationUuid")
    private final String organizationUuid;

    public CreateInvitationForOrganizationClientUserRequest(
            final Map<String, UserRoleModel> userRoles,
            final String email,
            final String inviterUserUuid,
            final String organizationUuid) {
        this.userRoles = userRoles;
        this.email = email;
        this.inviterUserUuid = inviterUserUuid;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public List<InvitationUserErrorResponseModel> validate() {
        if (MapUtils.isEmpty(userRoles)) {
            return Collections.singletonList(MISSING_USER_ROLES);
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
                .append(userRoles, that.userRoles)
                .append(email, that.email)
                .append(inviterUserUuid, that.inviterUserUuid)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userRoles)
                .append(email)
                .append(inviterUserUuid)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userRoles", userRoles)
                .append("email", email)
                .append("inviterUserUuid", inviterUserUuid)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public Map<String, UserRoleModel> getUserRoles() {
        return userRoles;
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
