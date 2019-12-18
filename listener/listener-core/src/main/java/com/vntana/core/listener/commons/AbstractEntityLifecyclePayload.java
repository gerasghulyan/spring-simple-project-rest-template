package com.vntana.core.listener.commons;

import com.vntana.commons.persistence.domain.AbstractDomainEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Geras Ghulyan.
 * Date: 12/6/19
 * Time: 10:16 AM
 */
public abstract class AbstractEntityLifecyclePayload<T extends AbstractDomainEntity> implements EntityLifecyclePayload<T> {

    private final T entity;
    private final EntityLifecycle entityLifecycle;

    public AbstractEntityLifecyclePayload(final T entity, final EntityLifecycle entityLifecycle) {
        this.entity = entity;
        this.entityLifecycle = entityLifecycle;
    }

    @Override
    public EntityLifecycle lifecycle() {
        return entityLifecycle;
    }

    @Override
    public T entity() {
        return entity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractEntityLifecyclePayload)) {
            return false;
        }
        final AbstractEntityLifecyclePayload<?> that = (AbstractEntityLifecyclePayload<?>) o;
        return new EqualsBuilder()
                .append(entity, that.entity)
                .append(entityLifecycle, that.entityLifecycle)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(entity)
                .append(entityLifecycle)
                .toHashCode();
    }
}
