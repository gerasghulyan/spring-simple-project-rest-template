package com.vntana.core.service.whitelist.exception;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.commons.service.exception.EntityNotFoundForUuidException;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 3:13 PM
 */
public class WhitelistIpNotFoundException extends EntityNotFoundForUuidException {

    public WhitelistIpNotFoundException(final String uuid, final Class<? extends AbstractUuidAwareDomainEntity> entityClass) {
        super(uuid, entityClass);
    }
}
