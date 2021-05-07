package com.vntana.core.service.storage.client;

import com.vntana.core.domain.storage.client.StorageClientOrganizationKey;
import com.vntana.core.service.storage.client.dto.CreateStorageClientOrganizationKeyDto;

import java.util.Optional;

/**
 * Created by Geras Ghulyan
 * Date: 30-Apr-21
 * Time: 15:24
 */
public interface StorageClientOrganizationKeyService {

    StorageClientOrganizationKey create(final CreateStorageClientOrganizationKeyDto dto);

    Optional<StorageClientOrganizationKey> findByClientOrganization(final String clientUuid);

}
