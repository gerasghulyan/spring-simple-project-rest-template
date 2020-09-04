package com.vntana.core.domain.comment;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.user.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import static com.vntana.commons.persistence.domain.DBConstants.BIG_TEXT_LENGTH;

/**
 * Created by Geras Ghulyan
 * Date: 9/3/20
 * Time: 4:33 PM
 */
@Entity
@Table(name = "user_comment")
@Inheritance(
        strategy = InheritanceType.JOINED
)
@DiscriminatorColumn(name = "type")
public class AbstractUserComment extends AbstractUuidAwareDomainEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_id"), updatable = false)
    private User user;

    @Column(name = "message", nullable = false, length = BIG_TEXT_LENGTH)
    private String message;

    public AbstractUserComment() {
    }

    public AbstractUserComment(final User user, final String message) {
        this.user = user;
        this.message = message;
    }

    public AbstractUserComment(final String uuid, final User user, final String message) {
        super(uuid);
        this.user = user;
        this.message = message;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractUserComment)) {
            return false;
        }
        final AbstractUserComment that = (AbstractUserComment) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(user, that.user)
                .append(message, that.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(user)
                .append(message)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("user", user)
                .append("message", message)
                .toString();
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
