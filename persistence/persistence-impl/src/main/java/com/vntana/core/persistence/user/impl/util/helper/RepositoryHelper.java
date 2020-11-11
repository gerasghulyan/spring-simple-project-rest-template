package com.vntana.core.persistence.user.impl.util.helper;

import com.vntana.core.persistence.user.impl.util.retriever.RepositoryRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import java.util.Optional;

/**
 * Created by Vardan Aivazian
 * Date: 11.11.2020
 * Time: 15:40
 */
public class RepositoryHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryHelper.class);

    private RepositoryHelper() {
    }

    public static <T> Optional<T> findOrEmpty(final RepositoryRetriever<T> retriever) {
        try {
            return Optional.of(retriever.retrieve());
        } catch (NoResultException e) {
            LOGGER.debug("No result exception - {}", e.getMessage());
        }
        return Optional.empty();
    }
}
