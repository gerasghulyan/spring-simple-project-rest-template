package com.vntana.core.domain.token;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
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
@Table(name = "token_authentication",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_token_authentication_token", columnNames = {"token"})
        }
)
//TODO refactor to extend from abstract token
public class TokenAuthentication extends AbstractUuidAwareDomainEntity {

    @Column(name = "expiration")
    private LocalDateTime expiration;

    @Column(name = "token", nullable = false, updatable = false)
    private String token;

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
        super();
        this.token = token;
        this.user = user;
        this.expiration = expiration;
    }

    public TokenAuthentication(final String token, final LocalDateTime expiration, final User user, final Organization organization) {
        super();
        this.token = token;
        this.user = user;
        this.expiration = expiration;
        this.organization = organization;
    }

    public TokenAuthentication(final String token, final LocalDateTime expiration, final User user, final ClientOrganization clientOrganization) {
        super();
        this.token = token;
        this.user = user;
        this.expiration = expiration;
        this.clientOrganization = clientOrganization;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(final LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
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
                .append(expiration, tokenAuthentication.expiration)
                .append(token, tokenAuthentication.token)
                .append(getIdOrNull(user), getIdOrNull(tokenAuthentication.user))
                .append(getIdOrNull(organization), getIdOrNull(tokenAuthentication.organization))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(expiration)
                .append(token)
                .append(getIdOrNull(user))
                .append(getIdOrNull(organization))
                .append(getIdOrNull(clientOrganization))
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("expiration", expiration)
                .append("token", token)
                .append("userId", getIdOrNull(user))
                .append("organizationId", getIdOrNull(organization))
                .append("clientOrganizationId", getIdOrNull(clientOrganization))
                .toString();
    }

    public void expire() {
        this.expiration = LocalDateTime.now().plusSeconds(10);
    }

    public boolean isExpired() {
        return !LocalDateTime.now().isBefore(expiration);
    }
}
