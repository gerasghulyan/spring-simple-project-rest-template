package com.vntana.core.listener.whitelist;

import com.vntana.cache.service.whitelist.WhitelistIpCacheService;
import com.vntana.cache.service.whitelist.dto.CacheWhitelistCreateDto;
import com.vntana.core.domain.whitelist.WhitelistType;
import com.vntana.core.service.whitelist.mediator.dto.SaveWhitelistIpLifecycleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.springframework.util.Assert.notNull;

/**
 * Created by Geras Ghulyan
 * Date: 10.12.19
 * Time: 16:48
 */
@Component
@Lazy(false)
public class WhitelistIpCacheUpdateEventListenerImpl implements WhitelistIpCacheUpdateEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhitelistIpCacheUpdateEventListenerImpl.class);

    private final WhitelistIpCacheService whitelistIpCacheService;

    public WhitelistIpCacheUpdateEventListenerImpl(final WhitelistIpCacheService whitelistIpCacheService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.whitelistIpCacheService = whitelistIpCacheService;
    }

    @EventListener
    @Override
    public void handleEvent(final WhitelistIpLifecyclePayload payload) {
        notNull(payload, "The WhitelistIpLifecyclePayload should not be null");
        final SaveWhitelistIpLifecycleDto whitelistIpLifecycleDto = payload.getDto();
        // TODO: @Diana please cleanup this after release
        if (whitelistIpLifecycleDto.getType().equals(WhitelistType.API)) {
            final CacheWhitelistCreateDto dto = new CacheWhitelistCreateDto(
                    whitelistIpLifecycleDto.getOrganizationUuid(),
                    whitelistIpLifecycleDto.getOrganizationSlug(),
                    whitelistIpLifecycleDto.getIps()
                    // TODO: 5/20/21 add ip type here 
            );
            whitelistIpCacheService.cacheByOrganizationUuid(dto);
            whitelistIpCacheService.cacheByOrganizationSlug(dto);
        }
    }
}