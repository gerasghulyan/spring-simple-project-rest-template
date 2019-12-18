package com.vntana.core.listener.commons;

import com.vntana.commons.persistence.domain.AbstractDomainEntity;

/**
 * Created by Geras Ghulyan.
 * Date: 12/6/19
 * Time: 10:15 AM
 */
public interface EntityLifecyclePayload<T extends AbstractDomainEntity> {

    EntityLifecycle lifecycle();

    T entity();
}
