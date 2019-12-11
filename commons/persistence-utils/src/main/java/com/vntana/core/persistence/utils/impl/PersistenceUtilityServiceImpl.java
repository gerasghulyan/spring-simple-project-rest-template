package com.vntana.core.persistence.utils.impl;


import com.vntana.core.persistence.utils.Executable;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.concurrent.Executor;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 11/15/14
 * Time: 9:50 AM
 */
@Service(value = "persistenceUtilityService")
public class PersistenceUtilityServiceImpl implements PersistenceUtilityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceUtilityServiceImpl.class);

    private final EntityManagerFactory entityManagerFactory;

    private final Executor executor;

    PersistenceUtilityServiceImpl(
            final EntityManagerFactory entityManagerFactory,
            @Qualifier("applicationThreadPoolTaskExecutor") final Executor executor) {
        super();
        this.entityManagerFactory = entityManagerFactory;
        this.executor = executor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T initializeAndUnProxy(final T entity) {
        Assert.notNull(entity, "Entity should not be null");
        LOGGER.debug("UnProxying entity - {}", new Object[]{entity});
        // UnProxied entity
        T unProxiedEntity = entity;
        // Initialize
        Hibernate.initialize(unProxiedEntity);
        if (unProxiedEntity instanceof HibernateProxy) {
            unProxiedEntity = (T) ((HibernateProxy) unProxiedEntity).getHibernateLazyInitializer().getImplementation();
        }
        return unProxiedEntity;
    }

    @Override
    public void runInPersistenceSession(final Executable executable) {
        assertExecutable(executable);
        boolean participate = false;
        if (TransactionSynchronizationManager.hasResource(entityManagerFactory)) {
            // Do not modify the EntityManager: just set the participate flag.
            participate = true;
        } else {
            LOGGER.debug("Opening JPA EntityManager in Spring Job");
            try {
                final EntityManager em = entityManagerFactory.createEntityManager();
                TransactionSynchronizationManager.bindResource(entityManagerFactory, new EntityManagerHolder(em));
            } catch (PersistenceException ex) {
                throw new DataAccessResourceFailureException("Could not create JPA EntityManager", ex);
            }
        }
        try {
            executable.execute();
        } finally {
            if (!participate) {
                EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.unbindResource(entityManagerFactory);
                LOGGER.debug("Closing JPA EntityManager in Spring Job");
                EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
            }
        }
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    @Override
    public void runInNewTransaction(final Executable executable) {
        assertExecutable(executable);
        LOGGER.debug("Running task in new transaction");
        executable.execute();
    }

    @Transactional
    @Override
    public void runInTransaction(final Executable executable) {
        assertExecutable(executable);
        LOGGER.debug("Running task in transaction");
        executable.execute();
    }

    @Override
    public void runAfterTransactionCommitIsSuccessful(final Executable executable, final boolean executeAsynchronously) {
        assertExecutable(executable);
        LOGGER.debug("Registering task as commit synchronization, asynchronous - {}", executeAsynchronously);
        TransactionSynchronizationManager.registerSynchronization(new SuccessfulCommitTask(executable, executeAsynchronously));
    }

    /* Utility methods */
    private void assertExecutable(final Executable executable) {
        Assert.notNull(executable, "Executable should not be null");
    }

    /* Inner class */
    private class SuccessfulCommitTask extends TransactionSynchronizationAdapter {

        /* Properties */
        private final Executable executable;

        private final boolean executeAsynchronously;

        /* Constructors */
        SuccessfulCommitTask(final Executable executable, final boolean executeAsynchronously) {
            this.executable = executable;
            this.executeAsynchronously = executeAsynchronously;
        }

        @Override
        public void afterCommit() {
            if (executeAsynchronously) {
                executor.execute(() -> runInPersistenceSession(executable));
            } else {
                executable.execute();
            }
        }
    }
}
