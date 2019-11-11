package com.vntana.core.service.common.exception;

import com.vntana.core.domain.commons.AbstractUuidAwareDomainEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/30/19
 * Time: 3:01 PM
 */
public class EntityNotFoundForUuidException extends RuntimeException {

    private final String uuid;
    private final Class<? extends AbstractUuidAwareDomainEntity> entityClass;

    public EntityNotFoundForUuidException(final String uuid, final Class<? extends AbstractUuidAwareDomainEntity> entityClass) {
        super(String.format("No entity for uuid - %s for class - %s was found", uuid, entityClass));
        this.uuid = uuid;
        this.entityClass = entityClass;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("entityClass", entityClass)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public Class<? extends AbstractUuidAwareDomainEntity> getEntityClass() {
        return entityClass;
    }
}
