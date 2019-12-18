package com.vntana.core.domain.token;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 12:15 PM
 */
@Entity
@Table(name = "token")
@Inheritance(
        strategy = InheritanceType.JOINED
)
@DiscriminatorColumn(name = "type")
public abstract class AbstractToken extends AbstractUuidAwareDomainEntity {

    @Column(name = "token", nullable = false, updatable = false, unique = true)
    private String token;

    AbstractToken() {
    }

    public AbstractToken(final String token) {
        this.token = token;
    }

    public abstract TokenType getType();

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractToken)) {
            return false;
        }
        final AbstractToken that = (AbstractToken) o;
        return new EqualsBuilder()
                .append(getUuid(), that.getUuid())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getUuid())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", token)
                .toString();
    }
}
