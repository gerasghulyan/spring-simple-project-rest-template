package com.vntana.core.service.token.auth.exception;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.service.common.exception.EntityNotFoundForUuidException;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 4:12 PM
 */
public class AuthTokenNotFoundForUuidException extends EntityNotFoundForUuidException {

    public AuthTokenNotFoundForUuidException(final String uuid, final Class<? extends AbstractUuidAwareDomainEntity> entityClass) {
        super(uuid, entityClass);
    }
}
