package com.vntana.core.service.user.exception;

import com.vntana.core.domain.commons.AbstractUuidAwareDomainEntity;
import com.vntana.core.service.common.exception.EntityNotFoundForUuidException;

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
