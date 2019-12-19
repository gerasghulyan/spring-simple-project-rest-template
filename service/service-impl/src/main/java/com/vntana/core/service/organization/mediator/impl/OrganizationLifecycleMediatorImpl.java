package com.vntana.core.service.organization.mediator.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.listener.commons.EntityLifecycle;
import com.vntana.core.listener.organization.OrganizationLifecyclePayload;
import com.vntana.core.service.organization.mediator.OrganizationLifecycleMediator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Geras Ghulyan
 * Date: 10.12.19
 * Time: 15:17
 */
@Component
public class OrganizationLifecycleMediatorImpl implements OrganizationLifecycleMediator {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationLifecycleMediatorImpl.class);
    private final ApplicationEventPublisher applicationEventPublisher;

    public OrganizationLifecycleMediatorImpl(final ApplicationEventPublisher applicationEventPublisher) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void onCreated(final Organization organization) {
        assertOrganization(organization);
        LOGGER.debug("Publishing organization creation event for uuid - {}", organization.getUuid());
        applicationEventPublisher.publishEvent(new OrganizationLifecyclePayload(organization, EntityLifecycle.CREATED));
        LOGGER.debug("Successfully published organization creation event for uuid - {}", organization.getUuid());
    }

    @Override
    public void onUpdated(final Organization organization) {
        throw new UnsupportedOperationException("Currently we are not supporting onUpdate case");
    }

    @Override
    public void onDeleted(final Organization organization) {
        throw new UnsupportedOperationException("Currently we are not supporting onUpdate case");
    }

    private void assertOrganization(final Organization organization) {
        Assert.notNull(organization, "The organization should not be null");
    }
}
