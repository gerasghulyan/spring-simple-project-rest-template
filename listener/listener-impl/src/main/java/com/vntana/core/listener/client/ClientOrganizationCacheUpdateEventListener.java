package com.vntana.core.listener.client;

import com.vntana.cache.service.client.ClientOrganizationCacheService;
import com.vntana.cache.service.client.dto.CacheClientOrganizationCreateDto;
import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.listener.commons.EntityLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Geras Ghulyan
 * Date: 10.12.19
 * Time: 16:48
 */
@Component
@Lazy(false)
public class ClientOrganizationCacheUpdateEventListener implements EntityLifecycleListener<ClientOrganization, ClientOrganizationLifecyclePayload> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientOrganizationCacheUpdateEventListener.class);

    private ClientOrganizationCacheService clientOrganizationCacheService;

    public ClientOrganizationCacheUpdateEventListener(final ClientOrganizationCacheService clientOrganizationCacheService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.clientOrganizationCacheService = clientOrganizationCacheService;
    }

    @EventListener
    @Override
    public void handleEvent(final ClientOrganizationLifecyclePayload payload) {
        Assert.notNull(payload, "The ClientOrganizationLifecyclePayload should not be null");
        final ClientOrganization client = payload.entity();
        final CacheClientOrganizationCreateDto dto = new CacheClientOrganizationCreateDto(
                client.getUuid(),
                client.getName(),
                client.getSlug(),
                client.getOrganization().getUuid(),
                client.getImageBlobId()
        );
        clientOrganizationCacheService.cacheByUuid(dto);
        clientOrganizationCacheService.cacheBySlug(dto);
    }
}
