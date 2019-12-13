package com.vntana.core.listener.organization;

import com.vntana.cache.service.organization.OrganizationCacheService;
import com.vntana.cache.service.organization.dto.CacheOrganizationCreateDto;
import com.vntana.core.domain.organization.Organization;
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
public class OrganizationCacheUpdateEventListener implements EntityLifecycleListener<Organization, OrganizationLifecyclePayload> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationCacheUpdateEventListener.class);

    private OrganizationCacheService organizationCacheService;

    public OrganizationCacheUpdateEventListener(final OrganizationCacheService organizationCacheService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationCacheService = organizationCacheService;
    }

    @EventListener
    @Override
    public void handleEvent(final OrganizationLifecyclePayload payload) {
        Assert.notNull(payload, "The ProductLifecyclePayload should not be null");
        final CacheOrganizationCreateDto dto = new CacheOrganizationCreateDto(
                payload.entity().getUuid(),
                payload.entity().getSlug(),
                payload.entity().getName()
        );
        organizationCacheService.cacheByUuid(dto);
        organizationCacheService.cacheBySlug(dto);
    }
}
