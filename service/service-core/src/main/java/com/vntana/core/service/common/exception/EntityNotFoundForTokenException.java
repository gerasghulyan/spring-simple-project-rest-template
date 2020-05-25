package com.vntana.core.service.common.exception;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Geras Ghulyan.
 * Date: 10/30/19
 * Time: 3:01 PM
 */
public class EntityNotFoundForTokenException extends RuntimeException {

    private final String token;
    private final Class<? extends AbstractUuidAwareDomainEntity> entityClass;

    public EntityNotFoundForTokenException(final String token, final Class<? extends AbstractUuidAwareDomainEntity> entityClass) {
        super(String.format("No entity for token - %s for class - %s was found", token, entityClass));
        this.token = token;
        this.entityClass = entityClass;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", token)
                .append("entityClass", entityClass)
                .toString();
    }

    public String getToken() {
        return token;
    }

    public Class<? extends AbstractUuidAwareDomainEntity> getEntityClass() {
        return entityClass;
    }
}
