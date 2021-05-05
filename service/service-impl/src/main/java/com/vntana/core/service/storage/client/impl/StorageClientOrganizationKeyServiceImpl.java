package com.vntana.core.service.storage.client.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.storage.client.StorageClientOrganizationKey;
import com.vntana.core.domain.storage.client.StorageClientOrganizationKeyType;
import com.vntana.core.persistence.storage.client.StorageClientOrganizationKeyRepository;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.storage.client.StorageClientOrganizationKeyService;
import com.vntana.core.service.storage.client.dto.CreateStorageClientOrganizationKeyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Geras Ghulyan
 * Date: 30-Apr-21
 * Time: 15:28
 */
@Service
public class StorageClientOrganizationKeyServiceImpl implements StorageClientOrganizationKeyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageClientOrganizationKeyServiceImpl.class);
    private static final String CUSTOMER_MANAGED_KEY_NAME = UUID.randomUUID().toString();
    private static final StorageClientOrganizationKeyType CUSTOMER_MANAGED_KEY_TYPE = StorageClientOrganizationKeyType.CUSTOMER_MANAGED_KEY;

    private final StorageClientOrganizationKeyRepository storageClientOrganizationKeyRepository;
    private final ClientOrganizationService clientOrganizationService;

    public StorageClientOrganizationKeyServiceImpl(
            final StorageClientOrganizationKeyRepository storageClientOrganizationKeyRepository,
            final ClientOrganizationService clientOrganizationService) {
        this.storageClientOrganizationKeyRepository = storageClientOrganizationKeyRepository;
        this.clientOrganizationService = clientOrganizationService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    @Transactional
    public StorageClientOrganizationKey create(final CreateStorageClientOrganizationKeyDto dto) {
        Assert.notNull(dto, "The CreateStorageClientOrganizationKeyDto should not be null or empty");
        LOGGER.debug("Creating StorageClientOrganizationKey for dto - {}", dto);
        final StorageClientOrganizationKey storageClientOrganizationKey = getStorageClientOrganizationKey(dto);
        final StorageClientOrganizationKey savedStorageClientOrganizationKey = storageClientOrganizationKeyRepository.save(storageClientOrganizationKey);
        LOGGER.debug("Successfully created StorageClientOrganizationKey for dto - {}", dto);
        return savedStorageClientOrganizationKey;
    }

    @Transactional(readOnly = true)
    public Optional<StorageClientOrganizationKey> findByClientOrganization(final String clientUuid) {
        Assert.hasText(clientUuid, "The clientUuid should not be null or empty");
        LOGGER.debug("Trying to get StorageClientOrganizationKey for clientUuid - {}", clientUuid);
        final ClientOrganization client = clientOrganizationService.getByUuid(clientUuid);
        final Optional<StorageClientOrganizationKey> storageClientOrganizationKey = storageClientOrganizationKeyRepository.findByClientOrganizationId(client.getId());
        LOGGER.debug("Successfully got StorageClientOrganizationKey for clientUuid - {}", clientUuid);
        return storageClientOrganizationKey;
    }

    private StorageClientOrganizationKey getStorageClientOrganizationKey(final CreateStorageClientOrganizationKeyDto dto) {
        return new StorageClientOrganizationKey(
                clientOrganizationService.getByUuid(dto.getClientUuid()),
                CUSTOMER_MANAGED_KEY_NAME,
                CUSTOMER_MANAGED_KEY_TYPE
        );
    }
}
