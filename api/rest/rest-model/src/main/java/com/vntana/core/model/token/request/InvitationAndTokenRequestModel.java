package com.vntana.core.model.token.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 12:14 PM
 */
public class InvitationAndTokenRequestModel {
    
    private String userInvitationUuid;
    
    private String token;

    public InvitationAndTokenRequestModel() {
    }

    public InvitationAndTokenRequestModel(final String userInvitationUuid, final String token) {
        this.userInvitationUuid = userInvitationUuid;
        this.token = token;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvitationAndTokenRequestModel)) {
            return false;
        }
        final InvitationAndTokenRequestModel that = (InvitationAndTokenRequestModel) o;
        return new EqualsBuilder()
                .append(userInvitationUuid, that.userInvitationUuid)
                .append(token, that.token)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userInvitationUuid)
                .append(token)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userInvitationUuid", userInvitationUuid)
                .append("token", token)
                .toString();
    }

    public String getUserInvitationUuid() {
        return userInvitationUuid;
    }

    public String getToken() {
        return token;
    }
}
