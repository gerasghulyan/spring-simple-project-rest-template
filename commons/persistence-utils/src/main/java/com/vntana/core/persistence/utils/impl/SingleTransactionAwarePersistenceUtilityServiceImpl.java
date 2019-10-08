package com.vntana.core.persistence.utils.impl;


import com.vntana.core.persistence.utils.Executable;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/16/15
 * Time: 6:15 PM
 */
@Service(value = "singleTransactionAwarePersistenceUtilityServiceImpl")
public class SingleTransactionAwarePersistenceUtilityServiceImpl implements PersistenceUtilityService {

    public SingleTransactionAwarePersistenceUtilityServiceImpl() {
        super();
    }

    @Override
    @Transactional
    public void runInNewTransaction(final Executable executable) {
        executable.execute();
    }

    @Override
    public void runAfterTransactionCommitIsSuccessful(final Executable executable, final boolean executeAsynchronously) {
        executable.execute();
    }

    @Override
    public <T> T initializeAndUnProxy(final T entity) {
        return entity;
    }

    @Override
    public void runInPersistenceSession(final Executable executable) {
        executable.execute();
    }

    @Override
    public void runInTransaction(final Executable executable) {
        executable.execute();
    }
}
