package com.project.sample.commons.persistence.domain;

import com.project.sample.commons.persistence.commons.domain.Entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Geras Ghulyan.
 * Date: 10/3/19
 * Time: 5:38 PM
 */
@MappedSuperclass
public abstract class AbstractDomainEntity implements Entity {

    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @GenericGenerator(
            name = "id_Sequence",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "optimizer", value = "hilo"),
                    @Parameter(name = "increment_size", value = "1")})
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "updated")
    private LocalDateTime updated;

    @Column(name = "removed")
    private LocalDateTime removed;
    //endregion

    //region Constructors
    public AbstractDomainEntity() {
    }

    public <T extends AbstractDomainEntity> Long getIdOrNull(T entity) {
        return entity == null ? null : entity.getId();
    }
    //endregion

    //region Public
    @PrePersist
    protected void onPrePersist() {
        this.created = LocalDateTime.now();
        this.updated = this.created;
    }

    @PreUpdate
    protected void onPreUpdate() {
        this.updated = LocalDateTime.now();
    }
    //endregion

    //region Equals, HashCode and ToString
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
                .append(removed, that.removed)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(created)
                .append(updated)
                .append(removed)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("created", created)
                .append("updated", updated)
                .append("removed", removed)
                .toString();
    }
    //endregion

    //region Properties getters and setters
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

    public LocalDateTime getRemoved() {
        return removed;
    }

    public void setRemoved(final LocalDateTime removed) {
        this.removed = removed;
    }
    //endregion
}
