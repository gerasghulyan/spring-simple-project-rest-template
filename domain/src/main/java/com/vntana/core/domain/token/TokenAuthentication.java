package com.vntana.core.domain.token;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 12:05 PM
 */
@Entity
@Table(name = "token_authentication")
@DiscriminatorValue(value = "AUTHENTICATION")
public class TokenAuthentication extends AbstractToken {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_token_authentication_user_id"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "organization_id", updatable = false, foreignKey = @ForeignKey(name = "fk_token_authentication_organization_id"))
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "client_organization_id", updatable = false, foreignKey = @ForeignKey(name = "fk_token_authentication_client_organization_id"))
    private ClientOrganization clientOrganization;

    TokenAuthentication() {
        super();
    }

    public TokenAuthentication(final String token, final LocalDateTime expiration, final User user) {
        super(token, expiration);
        this.user = user;
    }

    public TokenAuthentication(final String token, final LocalDateTime expiration, final User user, final Organization organization) {
        super(token, expiration);
        this.user = user;
        this.organization = organization;
    }

    public TokenAuthentication(final String token, final LocalDateTime expiration, final User user, final ClientOrganization clientOrganization) {
        super(token, expiration);
        this.user = user;
        this.clientOrganization = clientOrganization;
    }

    public User getUser() {
        return user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public ClientOrganization getClientOrganization() {
        return clientOrganization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenAuthentication)) {
            return false;
        }
        final TokenAuthentication tokenAuthentication = (TokenAuthentication) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getIdOrNull(user), getIdOrNull(tokenAuthentication.user))
                .append(getIdOrNull(organization), getIdOrNull(tokenAuthentication.organization))
                .append(getIdOrNull(clientOrganization), getIdOrNull(tokenAuthentication.clientOrganization))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(getIdOrNull(user))
                .append(getIdOrNull(organization))
                .append(getIdOrNull(clientOrganization))
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userId", getIdOrNull(user))
                .append("organizationId", getIdOrNull(organization))
                .append("clientOrganizationId", getIdOrNull(clientOrganization))
                .toString();
    }

    @Override
    public boolean isExpired() {
        return !LocalDateTime.now().isBefore(getExpiration());
    }

    @Override
    public TokenType getType() {
        return TokenType.AUTHENTICATION;
    }
}
