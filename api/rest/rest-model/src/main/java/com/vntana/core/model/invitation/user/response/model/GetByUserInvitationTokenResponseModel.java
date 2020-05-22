package com.vntana.core.model.invitation.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import com.vntana.core.model.invitation.InvitationStatusModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 4:22 PM
 */
public class GetByUserInvitationTokenResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("invitedUserEmail")
    private String invitedUserEmail;

    @JsonProperty("invitedUserExists")
    private Boolean invitedUserExists;

    @JsonProperty("organizationName")
    private String organizationName;

    @JsonProperty("inviterUserFullName")
    private String inviterUserFullName;

    @JsonProperty("status")
    private InvitationStatusModel status;

    public GetByUserInvitationTokenResponseModel() {
        super();
    }

    public GetByUserInvitationTokenResponseModel(final String uuid, final String invitedUserEmail, final Boolean invitedUserExists, final String organizationName, final String inviterUserFullName, final InvitationStatusModel status) {
        super(uuid);
        this.invitedUserEmail = invitedUserEmail;
        this.invitedUserExists = invitedUserExists;
        this.organizationName = organizationName;
        this.inviterUserFullName = inviterUserFullName;
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof GetByUserInvitationTokenResponseModel)) return false;

        final GetByUserInvitationTokenResponseModel that = (GetByUserInvitationTokenResponseModel) o;

        return new EqualsBuilder()
                .append(invitedUserEmail, that.invitedUserEmail)
                .append(invitedUserExists, that.invitedUserExists)
                .append(organizationName, that.organizationName)
                .append(inviterUserFullName, that.inviterUserFullName)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(invitedUserEmail)
                .append(invitedUserExists)
                .append(organizationName)
                .append(inviterUserFullName)
                .append(status)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("invitedUserEmail", invitedUserEmail)
                .append("invitedUserExists", invitedUserExists)
                .append("organizationName", organizationName)
                .append("inviterUserFullName", inviterUserFullName)
                .append("status", status)
                .toString();
    }

    public String getInvitedUserEmail() {
        return invitedUserEmail;
    }

    public Boolean getInvitedUserExists() {
        return invitedUserExists;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getInviterUserFullName() {
        return inviterUserFullName;
    }

    public InvitationStatusModel getStatus() {
        return status;
    }
}