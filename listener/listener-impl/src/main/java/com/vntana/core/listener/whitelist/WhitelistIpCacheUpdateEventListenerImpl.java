package com.vntana.core.listener.whitelist;

import com.vntana.cache.service.whitelist.WhitelistIpCacheService;
import com.vntana.cache.service.whitelist.dto.CacheWhitelistCreateDto;
import com.vntana.core.service.whitelist.mediator.dto.SaveWhitelistIpLifecycleDto;
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
public class WhitelistIpCacheUpdateEventListenerImpl implements WhitelistIpCacheUpdateEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhitelistIpCacheUpdateEventListenerImpl.class);

    private WhitelistIpCacheService whitelistIpCacheService;

    public WhitelistIpCacheUpdateEventListenerImpl(final WhitelistIpCacheService whitelistIpCacheService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.whitelistIpCacheService = whitelistIpCacheService;
    }

    @EventListener
    @Override
    public void handleEvent(final WhitelistIpLifecyclePayload payload) {
        Assert.notNull(payload, "The WhitelistIpLifecyclePayload should not be null");
        final SaveWhitelistIpLifecycleDto whitelistIpLifecycleDto = payload.getDto();
        final CacheWhitelistCreateDto dto = new CacheWhitelistCreateDto(
                whitelistIpLifecycleDto.getOrganizationUuid(),
                whitelistIpLifecycleDto.getOrganizationSlug(),
                whitelistIpLifecycleDto.getIps()
        );
        whitelistIpCacheService.cacheByOrganizationUuid(dto);
        whitelistIpCacheService.cacheByOrganizationSlug(dto);
    }
}