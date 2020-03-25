package com.vntana.core.rest.facade.indexation.organization.component.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.rest.facade.indexation.organization.component.OrganizationIndexationComponent;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.organization.mediator.OrganizationUuidAwareLifecycleMediator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 10:14 AM
 */
@Component
public class OrganizationIndexationComponentImpl implements OrganizationIndexationComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationIndexationComponentImpl.class);

    private final OrganizationUuidAwareLifecycleMediator organizationUuidAwareLifecycleMediator;
    private final OrganizationService organizationService;

    public OrganizationIndexationComponentImpl(final OrganizationUuidAwareLifecycleMediator organizationUuidAwareLifecycleMediator,
                                               final OrganizationService organizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationUuidAwareLifecycleMediator = organizationUuidAwareLifecycleMediator;
        this.organizationService = organizationService;
    }

    @Override
    public void indexByOne(final String uuid) {
        Assert.hasText(uuid, "The organization uuid should not be null or empty");
        LOGGER.debug("Processing the re-indexation of an organizations having uuid - {}", uuid);
        final long startTime = System.currentTimeMillis();
        final Organization organization = organizationService.getByUuid(uuid);
        if (Objects.nonNull(organization.getRemoved())) {
            organizationUuidAwareLifecycleMediator.onDeleted(uuid);
        } else {
            organizationUuidAwareLifecycleMediator.onUpdated(uuid);
        }
        LOGGER.debug("Successfully processed the re-indexation of an organizations having uuid -{}  which took - {} millis", uuid, System.currentTimeMillis() - startTime);
    }
}