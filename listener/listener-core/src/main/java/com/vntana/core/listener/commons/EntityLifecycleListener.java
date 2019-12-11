package com.vntana.core.listener.commons;

import com.vntana.core.domain.commons.AbstractDomainEntity;

/**
 * Created by Geras Ghulyan.
 * Date: 12/6/19
 * Time: 12:01 PM
 */
public interface EntityLifecycleListener<E extends AbstractDomainEntity, T extends EntityLifecyclePayload<E>> {

    void handleEvent(T payload);
}
