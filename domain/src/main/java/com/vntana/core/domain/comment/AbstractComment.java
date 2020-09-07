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
@Table(name = "comment")
@Inheritance(
        strategy = InheritanceType.JOINED
)
@DiscriminatorColumn(name = "type")
public class AbstractComment extends AbstractUuidAwareDomainEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_id"), updatable = false)
    private User user;

    @Column(name = "message", nullable = false, length = BIG_TEXT_LENGTH)
    private String message;

    public AbstractComment() {
        super();
    }

    public AbstractComment(final User user, final String message) {
        this.user = user;
        this.message = message;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractComment)) {
            return false;
        }
        final AbstractComment that = (AbstractComment) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getIdOrNull(user), getIdOrNull(that.user))
                .append(message, that.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(getIdOrNull(user))
                .append(message)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("user_id", getIdOrNull(user))
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
