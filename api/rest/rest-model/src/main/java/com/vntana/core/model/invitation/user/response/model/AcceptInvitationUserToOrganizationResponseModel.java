package com.vntana.core.model.invitation.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 5:31 PM
 */
public class AcceptInvitationUserToOrganizationResponseModel extends AbstractAcceptUserInvitationResponseModel {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public AcceptInvitationUserToOrganizationResponseModel() {
        super();
    }

    public AcceptInvitationUserToOrganizationResponseModel(
            final String organizationUuid,
            final String userUuid,
            final UserRoleModel userRoleModel) {
        super(userUuid, userRoleModel);
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcceptInvitationUserToOrganizationResponseModel)) {
            return false;
        }
        final AcceptInvitationUserToOrganizationResponseModel that = (AcceptInvitationUserToOrganizationResponseModel) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }
}
