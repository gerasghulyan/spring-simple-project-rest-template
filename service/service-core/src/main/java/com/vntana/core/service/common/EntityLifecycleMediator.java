package com.vntana.core.service.common;

import com.vntana.core.domain.commons.AbstractDomainEntity;

/**
 * Created by Geras Ghulyan.
 * Date: 12/5/19
 * Time: 1:52 PM
 */
public interface EntityLifecycleMediator<T extends AbstractDomainEntity> {

    void onCreated(final T entity);

    void onUpdated(final T entity);

    void onDeleted(final T entity);
}
