package com.vntana.core.domain.token;

import com.vntana.core.domain.user.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Geras Ghulyan
 * Date: 6/22/20
 * Time: 6:40 PM
 */
@Entity
@Table(name = "token_reset_password")
@DiscriminatorValue(value = "TOKEN_RESET_PASSWORD")
public class TokenResetPassword extends AbstractToken {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_token_reset_password_user_id")
    )
    private User user;

    public TokenResetPassword() {
    }

    public TokenResetPassword(final String token, final User user) {
        super(token);
        this.user = user;
    }

    public TokenResetPassword(final String token, final LocalDateTime expiration, final User user) {
        super(token, expiration);
        this.user = user;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenResetPassword)) {
            return false;
        }
        final TokenResetPassword that = (TokenResetPassword) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getIdOrNull(user), getIdOrNull(that.user))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(getIdOrNull(user))
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("user", getIdOrNull(user))
                .toString();
    }

    @Override
    public TokenType getType() {
        return TokenType.RESET_PASSWORD;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
