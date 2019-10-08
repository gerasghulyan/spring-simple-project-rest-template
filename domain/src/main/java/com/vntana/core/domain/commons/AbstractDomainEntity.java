package com.vntana.core.domain.commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 5:38 PM
 */
@MappedSuperclass
public abstract class AbstractDomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence")
    private Long id;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "updated")
    private LocalDateTime updated;

    public AbstractDomainEntity() {
        super();
    }

    public <T extends AbstractDomainEntity> Long getIdOrNull(T entity) {
        return entity == null ? null : entity.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(final LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractDomainEntity)) {
            return false;
        }
        final AbstractDomainEntity that = (AbstractDomainEntity) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(created, that.created)
                .append(updated, that.updated)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(created)
                .append(updated)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("created", created)
                .append("updated", updated)
                .toString();
    }
}
