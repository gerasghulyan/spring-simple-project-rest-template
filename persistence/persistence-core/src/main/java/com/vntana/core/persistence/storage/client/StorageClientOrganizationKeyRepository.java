package com.vntana.core.persistence.storage.client;

import com.vntana.core.domain.storage.client.StorageClientOrganizationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 10:41
 */
@Repository
public interface StorageClientOrganizationKeyRepository extends JpaRepository<StorageClientOrganizationKey, Long> {

    Optional<StorageClientOrganizationKey> findByClientOrganizationId(final Long clientId);

}
