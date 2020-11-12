package com.vntana.core.persistence.custom.entity.manager;

import com.vntana.commons.persistence.domain.AbstractDomainEntity;

/**
 * Created by Vardan Aivazian
 * Date: 11.11.2020
 * Time: 15:37
 */
@FunctionalInterface
public interface CustomPersistenceEntityManager<T extends AbstractDomainEntity> {
    T find();
}
