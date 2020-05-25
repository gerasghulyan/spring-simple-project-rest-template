package com.vntana.core.domain.token;

import com.vntana.core.domain.invitation.user.InvitationUser;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:08 PM
 */
@Entity
@Table(name = "token_invitation_user")
@DiscriminatorValue(value = "INVITATION_ORGANIZATION")
public class TokenInvitationUser extends AbstractToken {

    @ManyToOne
    @JoinColumn(name = "invitation_user_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_token_invitation_user_invitation_user_id")
    )
    private InvitationUser invitationUser;

    TokenInvitationUser() {
        super();
    }

    public TokenInvitationUser(final String token, final InvitationUser invitationUser) {
        super(token);
        this.invitationUser = invitationUser;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenInvitationUser)) {
            return false;
        }
        final TokenInvitationUser that = (TokenInvitationUser) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(invitationUser, that.invitationUser)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(invitationUser)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("invitationUser", invitationUser)
                .toString();
    }

    @Override
    public TokenType getType() {
        return TokenType.INVITATION_USER;
    }

    public InvitationUser getInvitationUser() {
        return invitationUser;
    }
}
