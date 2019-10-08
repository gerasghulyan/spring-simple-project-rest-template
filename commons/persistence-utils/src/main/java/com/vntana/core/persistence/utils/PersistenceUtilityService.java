package com.vntana.core.persistence.utils;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 11/15/14
 * Time: 9:49 AM
 */
public interface PersistenceUtilityService {

    /**
     * UnProxies entity. Useful when using lazy initialization with class hierarchies during persistence
     *
     * @param entity entity to be unProxies
     * @return unProxied entity
     */
    <T> T initializeAndUnProxy(final T entity);

    /**
     * Wrap task running in persistence session
     *
     * @param executable
     */
    void runInPersistenceSession(final Executable executable);

    /**
     * Runs task in new transaction
     *
     * @param executable
     */
    void runInNewTransaction(final Executable executable);

    /**
     * Runs task in transaction
     *
     * @param executable
     */
    void runInTransaction(final Executable executable);

    /**
     * Runs task in after current transaction successfully commits
     *
     * @param executable
     * @param executeAsynchronously;
     */
    void runAfterTransactionCommitIsSuccessful(final Executable executable, final boolean executeAsynchronously);
}
