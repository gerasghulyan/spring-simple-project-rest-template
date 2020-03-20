package com.vntana.core.service.token.exception;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.service.common.exception.EntityNotFoundForUuidException;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 4:12 PM
 */
public class TokenNotFoundForUuidException extends EntityNotFoundForUuidException {

    public TokenNotFoundForUuidException(final String uuid, final Class<? extends AbstractUuidAwareDomainEntity> entityClass) {
        super(uuid, entityClass);
    }
}
