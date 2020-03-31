package com.vntana.core.domain.token;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
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
@Table(name = "auth_token",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_auth_token_token", columnNames = {"token"})
        }
)
public class AuthToken extends AbstractUuidAwareDomainEntity {

    @Column(name = "expiration")
    private LocalDateTime expiration;

    @Column(name = "token", nullable = false, updatable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_auth_token_user_id"))
    private User user;

    AuthToken() {
        super();
    }

    public AuthToken(final String token, final User user) {
        super();
        this.token = token;
        this.user = user;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthToken)) {
            return false;
        }
        final AuthToken authToken = (AuthToken) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(expiration, authToken.expiration)
                .append(token, authToken.token)
                .append(getIdOrNull(user), getIdOrNull(authToken.user))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(expiration)
                .append(token)
                .append(getIdOrNull(user))
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("expiration", expiration)
                .append("token", token)
                .append("userId", getIdOrNull(user))
                .toString();
    }

    public void expire() {
        if (this.expiration == null) {
            this.expiration = LocalDateTime.now();
        }
    }

    public boolean isExpired() {
        return expiration != null;
    }
}
