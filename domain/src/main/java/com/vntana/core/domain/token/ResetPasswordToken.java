package com.vntana.core.domain.token;

import com.vntana.core.domain.user.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 12:19 PM
 */
@Entity
@Table(name = "token_reset_password")
@DiscriminatorValue("RESET_PASSWORD")
public class ResetPasswordToken extends AbstractToken {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;

    ResetPasswordToken() {
    }

    public ResetPasswordToken(final String token, final User user) {
        super(token);
        this.user = user;
    }

    @Override
    public TokenType getType() {
        return TokenType.RESET_PASSWORD;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResetPasswordToken)) {
            return false;
        }
        final ResetPasswordToken that = (ResetPasswordToken) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("user_id", getIdOrNull(user))
                .toString();
    }
}
