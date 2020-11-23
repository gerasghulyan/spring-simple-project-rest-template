package com.vntana.core.domain.token;

import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 2:25 PM
 */
@Entity
@Table(name = "token_user_invitation_to_organization_client")
@DiscriminatorValue(value = "INVITATION_USER_TO_CLIENT")
public class TokenUserInvitationToOrganizationClient extends AbstractToken {

    @ManyToOne
    @JoinColumn(name = "user_invitation_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_token_invitation_user_invitation_user_id")
    )
    private InvitationOrganizationClientUser userInvitation;

    public TokenUserInvitationToOrganizationClient() {
        super();
    }

    public TokenUserInvitationToOrganizationClient(final String token, final InvitationOrganizationClientUser userInvitation) {
        super(token);
        this.userInvitation = userInvitation;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenUserInvitationToOrganizationClient)) {
            return false;
        }
        final TokenUserInvitationToOrganizationClient that = (TokenUserInvitationToOrganizationClient) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(userInvitation, that.userInvitation)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(userInvitation)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userInvitation", userInvitation)
                .toString();
    }

    @Override
    public TokenType getType() {
        return TokenType.INVITATION_USER_TO_CLIENT;
    }

    public InvitationOrganizationClientUser getUserInvitation() {
        return userInvitation;
    }
}
