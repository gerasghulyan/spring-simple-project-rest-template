package com.vntana.core.model.invitation.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 3:25 PM
 */
public class GetAllByStatusUserInvitationsResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("role")
    private UserRoleModel role;

    @JsonProperty("email")
    private String email;

    public GetAllByStatusUserInvitationsResponseModel() {
        super();
    }

    public GetAllByStatusUserInvitationsResponseModel(final String uuid, final UserRoleModel role, final String email) {
        super(uuid);
        this.role = role;
        this.email = email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof GetAllByStatusUserInvitationsResponseModel)) return false;

        final GetAllByStatusUserInvitationsResponseModel that = (GetAllByStatusUserInvitationsResponseModel) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(role, that.role)
                .append(email, that.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(role)
                .append(email)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("role", role)
                .append("email", email)
                .toString();
    }

    public UserRoleModel getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }
}