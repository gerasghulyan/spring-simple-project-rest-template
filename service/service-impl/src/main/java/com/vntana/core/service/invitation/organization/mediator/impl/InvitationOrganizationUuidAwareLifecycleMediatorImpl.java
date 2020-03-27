package com.vntana.core.service.invitation.organization.mediator.impl;

import com.vntana.commons.indexation.payload.EntityLifecycle;
import com.vntana.core.listener.invitation.organization.InvitationOrganizationUuidAwareLifecyclePayload;
import com.vntana.core.service.invitation.organization.mediator.InvitationOrganizationUuidAwareLifecycleMediator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 5:24 PM
 */
@Component
public class InvitationOrganizationUuidAwareLifecycleMediatorImpl implements InvitationOrganizationUuidAwareLifecycleMediator {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationOrganizationUuidAwareLifecycleMediatorImpl.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public InvitationOrganizationUuidAwareLifecycleMediatorImpl(final ApplicationEventPublisher applicationEventPublisher) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void onCreated(final String uuid) {
        Assert.hasText(uuid, "The organization invitation uuid should not be null or empty");
        LOGGER.debug("Publishing organization creation event for uuid - {}", uuid);
        applicationEventPublisher.publishEvent(new InvitationOrganizationUuidAwareLifecyclePayload(uuid, EntityLifecycle.CREATED));
        LOGGER.debug("Successfully published organization invitation creation event for uuid - {}", uuid);
    }

    @Override
    public void onUpdated(final String uuid) {
        Assert.hasText(uuid, "The organization invitation uuid should not be null or empty");
        LOGGER.debug("Publishing organization update event for uuid - {}", uuid);
        applicationEventPublisher.publishEvent(new InvitationOrganizationUuidAwareLifecyclePayload(uuid, EntityLifecycle.UPDATED));
        LOGGER.debug("Successfully published organization invitation update event for uuid - {}", uuid);
    }

    @Override
    public void onDeleted(final String uuid) {
        Assert.hasText(uuid, "The organization invitation uuid should not be null or empty");
        LOGGER.debug("Publishing organization delete event for uuid - {}", uuid);
        applicationEventPublisher.publishEvent(new InvitationOrganizationUuidAwareLifecyclePayload(uuid, EntityLifecycle.DELETED));
        LOGGER.debug("Successfully published organization invitation delete event for uuid - {}", uuid);
    }
}
