package com.vntana.core.domain.token;

import com.vntana.core.domain.user.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Diana Gevorgyan
 * Date: 3/22/21
 * Time: 5:04 PM
 */
@Entity
@Table(name = "token_personal_access")
@DiscriminatorValue(value = "PERSONAL_ACCESS")
public class TokenPersonalAccess extends AbstractToken {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_token_personal_access_user_id")
    )
    private User user;

    public TokenPersonalAccess() {
    }

    public TokenPersonalAccess(
            final String token,
            final User user) {
        super(token);
        this.user = user;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenPersonalAccess)) {
            return false;
        }
        final TokenPersonalAccess that = (TokenPersonalAccess) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(user, that.user)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(user)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("user", user)
                .toString();
    }

    @Override
    public TokenType getType() {
        return TokenType.PERSONAL_ACCESS;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
