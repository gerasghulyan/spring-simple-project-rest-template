package com.vntana.core.persistence.user.impl.util.helper;

import com.vntana.commons.persistence.domain.AbstractDomainEntity;
import com.vntana.core.persistence.custom.entity.manager.CustomPersistenceEntityManager;
import io.vavr.control.Try;

import java.util.Optional;

/**
 * Created by Vardan Aivazian
 * Date: 11.11.2020
 * Time: 15:40
 */
public class RepositoryHelper {

    private RepositoryHelper() {
    }

    public static <T extends AbstractDomainEntity> Optional<T> find(final CustomPersistenceEntityManager<T> customPersistenceEntityManager) {
        return Try.of(customPersistenceEntityManager::find)
                .map(Optional::of)
                .getOrElse(Optional::empty);
    }
}
