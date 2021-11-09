package com.project.sample.commons.persistence.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Created by Geras Ghulyan.
 * Date: 10/3/19
 * Time: 6:16 PM
 */
@MappedSuperclass
public abstract class AbstractUuidAwareDomainEntity extends AbstractDomainEntity {

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private String uuid = UUID.randomUUID().toString();

    public AbstractUuidAwareDomainEntity() {
    }

    public AbstractUuidAwareDomainEntity(final String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractUuidAwareDomainEntity)) {
            return false;
        }
        final AbstractUuidAwareDomainEntity that = (AbstractUuidAwareDomainEntity) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(uuid, that.uuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(uuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .toString();
    }
}
