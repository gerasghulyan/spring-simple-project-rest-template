package com.vntana.core.listener.organization;

import com.vntana.cache.service.organization.OrganizationLockService;
import com.vntana.commons.indexation.listener.EntityUuidAwareLifecycleListener;
import com.vntana.commons.indexation.payload.EntityLifecycle;
import com.vntana.commons.queue.model.MessageActionType;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.indexation.consumer.message.organization.OrganizationUuidAwareActionQueueMessage;
import com.vntana.core.indexation.producer.organization.OrganizationUuidAwareActionProducer;
import com.vntana.core.service.organization.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 12:04 PM
 */
@Component
@Lazy(false)
public class OrganizationUuidAwareIndexationStartEventListener implements EntityUuidAwareLifecycleListener<OrganizationUuidAwareLifecyclePayload> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationCacheUpdateEventListener.class);

    private final OrganizationUuidAwareActionProducer organizationUuidAwareActionProducer;
    private final OrganizationService organizationService;
    private final OrganizationLockService organizationLockService;

    public OrganizationUuidAwareIndexationStartEventListener(final OrganizationUuidAwareActionProducer organizationUuidAwareActionProducer,
                                                             final OrganizationService organizationService, final OrganizationLockService organizationLockService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationUuidAwareActionProducer = organizationUuidAwareActionProducer;
        this.organizationService = organizationService;
        this.organizationLockService = organizationLockService;
    }

    @EventListener
    @Override
    public void handleEvent(final OrganizationUuidAwareLifecyclePayload payload) {
        Assert.notNull(payload, "The OrganizationUuidAwareLifecyclePayload should not be null");
        LOGGER.debug("Trying to produce OrganizationUuidAwareActionQueueMessage to organization pre-indexation topic for uuid - {}", payload.entityUuid());
        if (isUpdateEventWhenEntityRemoved(payload)) {
            LOGGER.debug("Exiting from indexation. Organization with uuid - {} is deleted. Update is not supported", payload.entityUuid());
            return;
        }
        organizationLockService.lock(payload.entityUuid());
        organizationUuidAwareActionProducer.produce(new OrganizationUuidAwareActionQueueMessage(MessageActionType.valueOf(payload.lifecycle().name()), payload.entityUuid()));
        LOGGER.debug("Successfully produced OrganizationUuidAwareActionQueueMessage to organization pre-indexation topic for uuid - {}", payload.entityUuid());
    }


    @Override
    public boolean isUpdateEventWhenEntityRemoved(final OrganizationUuidAwareLifecyclePayload payload) {
        final Organization organization = organizationService.getByUuid(payload.entityUuid());
        return Objects.nonNull(organization.getRemoved()) && payload.lifecycle() == EntityLifecycle.UPDATED;
    }
}