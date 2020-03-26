package com.vntana.core.service.organization.mediator.impl;

import com.vntana.commons.indexation.payload.EntityLifecycle;
import com.vntana.core.listener.organization.OrganizationUuidAwareLifecyclePayload;
import com.vntana.core.service.organization.mediator.OrganizationUuidAwareLifecycleMediator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Manuk Gharslyan.
 * Date: 3/24/2020
 * Time: 6:42 PM
 */
@Component
public class OrganizationUuidAwareLifecycleMediatorImpl implements OrganizationUuidAwareLifecycleMediator {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationLifecycleMediatorImpl.class);
    private final ApplicationEventPublisher applicationEventPublisher;

    public OrganizationUuidAwareLifecycleMediatorImpl(final ApplicationEventPublisher applicationEventPublisher) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void onCreated(final String uuid) {
        Assert.hasText(uuid, "The organization uuid should not be null or empty");
        LOGGER.debug("Publishing organization creation event for uuid - {}", uuid);
        applicationEventPublisher.publishEvent(new OrganizationUuidAwareLifecyclePayload(uuid, EntityLifecycle.CREATED));
        LOGGER.debug("Successfully published organization creation event for uuid - {}", uuid);
    }

    @Override
    public void onUpdated(final String uuid) {
        Assert.hasText(uuid, "The organization uuid should not be null or empty");
        LOGGER.debug("Publishing organization update event for uuid - {}", uuid);
        applicationEventPublisher.publishEvent(new OrganizationUuidAwareLifecyclePayload(uuid, EntityLifecycle.UPDATED));
        LOGGER.debug("Successfully published organization update event for uuid - {}", uuid);
    }

    @Override
    public void onDeleted(final String uuid) {
        Assert.hasText(uuid, "The organization uuid should not be null or empty");
        LOGGER.debug("Publishing organization delete event for uuid - {}", uuid);
        applicationEventPublisher.publishEvent(new OrganizationUuidAwareLifecyclePayload(uuid, EntityLifecycle.DELETED));
        LOGGER.debug("Successfully published organization delete event for uuid - {}", uuid);
    }
}