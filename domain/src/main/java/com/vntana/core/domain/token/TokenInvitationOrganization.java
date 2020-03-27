package com.vntana.core.domain.token;

import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 10:16 AM
 */
@Entity
@Table(name = "token_invitation_organization")
@DiscriminatorValue(value = "INVITATION_ORGANIZATION")
public class TokenInvitationOrganization extends AbstractToken {

    @ManyToOne
    @JoinColumn(name = "invitation_organization_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_token_invitation_organization_invitation_organization_id")
    )
    private InvitationOrganization invitationOrganization;

    TokenInvitationOrganization() {
        super();
    }

    public TokenInvitationOrganization(final String token, final InvitationOrganization invitationOrganization) {
        super(token);
        this.invitationOrganization = invitationOrganization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenInvitationOrganization)) {
            return false;
        }
        final TokenInvitationOrganization that = (TokenInvitationOrganization) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(invitationOrganization, that.invitationOrganization)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(invitationOrganization)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("invitationOrganization", invitationOrganization)
                .toString();
    }

    @Override
    public TokenType getType() {
        return TokenType.INVITATION_ORGANIZATION;
    }

    public InvitationOrganization getInvitationOrganization() {
        return invitationOrganization;
    }
}
