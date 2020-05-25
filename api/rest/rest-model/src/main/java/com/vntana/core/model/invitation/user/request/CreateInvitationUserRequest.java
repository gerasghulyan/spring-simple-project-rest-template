package com.vntana.core.model.invitation.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.auth.response.UserRoleModel;
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
 * Date: 5/12/2020
 * Time: 1:40 PM
 */
public class CreateInvitationUserRequest extends AbstractRequestModel implements ValidatableRequest<InvitationUserErrorResponseModel> {

    @JsonProperty("userRole")
    private UserRoleModel userRole;

    @JsonProperty("email")
    private String email;

    @JsonProperty("inviterUserUuid")
    private String inviterUserUuid;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public CreateInvitationUserRequest() {
        super();
    }

    public CreateInvitationUserRequest(final UserRoleModel userRole, final String email, final String inviterUserUuid, final String organizationUuid) {
        this.userRole = userRole;
        this.email = email;
        this.inviterUserUuid = inviterUserUuid;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public List<InvitationUserErrorResponseModel> validate() {
        if (userRole == null) {
            return Collections.singletonList(MISSING_USER_ROLE);
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
        if (this == o) return true;

        if (!(o instanceof CreateInvitationUserRequest)) return false;

        final CreateInvitationUserRequest that = (CreateInvitationUserRequest) o;

        return new EqualsBuilder()
                .append(userRole, that.userRole)
                .append(email, that.email)
                .append(inviterUserUuid, that.inviterUserUuid)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userRole)
                .append(email)
                .append(inviterUserUuid)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userRole", userRole)
                .append("email", email)
                .append("inviterUserUuid", inviterUserUuid)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public UserRoleModel getUserRole() {
        return userRole;
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