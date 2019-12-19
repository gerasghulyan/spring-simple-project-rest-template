package com.vntana.core.service.whitelist.mediator.impl;


import com.vntana.core.listener.whitelist.WhitelistIpLifecycle;
import com.vntana.core.listener.whitelist.WhitelistIpLifecyclePayload;
import com.vntana.core.service.whitelist.mediator.WhitelistIpLifecycleMediator;
import com.vntana.core.service.whitelist.mediator.dto.SaveWhitelistIpLifecycleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Geras Ghulyan
 * Date: 18.12.19
 * Time: 10:26
 */
@Component
public class WhitelistIpLifecycleMediatorImpl implements WhitelistIpLifecycleMediator {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhitelistIpLifecycleMediatorImpl.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public WhitelistIpLifecycleMediatorImpl(final ApplicationEventPublisher applicationEventPublisher) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void onSaved(final SaveWhitelistIpLifecycleDto dto) {
        Assert.notNull(dto, "The SaveWhitelistIpLifecycleDto dto should not be null");
        LOGGER.debug("Publishing whitelistIp creation event for dto - {}", dto);
        applicationEventPublisher.publishEvent(new WhitelistIpLifecyclePayload(dto, WhitelistIpLifecycle.SAVED));
        LOGGER.debug("Successfully published whitelistIp creation event for dto - {}", dto);
    }
}