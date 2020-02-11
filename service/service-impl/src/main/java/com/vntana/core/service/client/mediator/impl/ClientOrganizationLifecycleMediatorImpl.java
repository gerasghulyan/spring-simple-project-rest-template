package com.vntana.core.service.client.mediator.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.listener.client.ClientOrganizationLifecyclePayload;
import com.vntana.core.listener.commons.EntityLifecycle;
import com.vntana.core.service.client.mediator.ClientOrganizationLifecycleMediator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Geras Ghulyan
 * Date: 11.02.20
 * Time: 11:58
 */
@Component
public class ClientOrganizationLifecycleMediatorImpl implements ClientOrganizationLifecycleMediator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientOrganizationLifecycleMediatorImpl.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public ClientOrganizationLifecycleMediatorImpl(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public void onCreated(final ClientOrganization clientOrganization) {
        assertClientOrganization(clientOrganization);
        LOGGER.debug("Publishing clientOrganization creation event for uuid - {}", clientOrganization.getUuid());
        applicationEventPublisher.publishEvent(new ClientOrganizationLifecyclePayload(clientOrganization, EntityLifecycle.CREATED));
        LOGGER.debug("Successfully published clientOrganization creation event for uuid - {}", clientOrganization.getUuid());
    }

    @Override
    public void onUpdated(final ClientOrganization clientOrganization) {
        throw new UnsupportedOperationException("Currently we are not supporting `onDeleted` case");
    }

    @Override
    public void onDeleted(final ClientOrganization clientOrganization) {
        throw new UnsupportedOperationException("Currently we are not supporting `onDeleted` case");
    }

    private void assertClientOrganization(final ClientOrganization clientOrganization) {
        Assert.notNull(clientOrganization, "The ClientOrganization should not be null");
    }
}
