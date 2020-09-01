package com.vntana.core.service.user.exception;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.commons.service.exception.EntityNotFoundForUuidException;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/11/19
 * Time: 10:57 AM
 */
public class UserNotFoundForUuidException extends EntityNotFoundForUuidException {
    
    public UserNotFoundForUuidException(final String uuid, final Class<? extends AbstractUuidAwareDomainEntity> entityClass) {
        super(uuid, entityClass);
    }
}
