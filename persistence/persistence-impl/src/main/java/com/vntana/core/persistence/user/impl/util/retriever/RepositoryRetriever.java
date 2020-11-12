package com.vntana.core.persistence.user.impl.util.retriever;

/**
 * Created by Vardan Aivazian
 * Date: 11.11.2020
 * Time: 15:37
 */
@FunctionalInterface
public interface RepositoryRetriever<T> {
    T retrieve();
}
