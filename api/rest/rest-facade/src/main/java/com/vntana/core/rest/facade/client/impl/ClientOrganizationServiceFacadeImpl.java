package com.vntana.core.rest.facade.client.impl;

import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse;
import com.vntana.core.rest.facade.client.ClientOrganizationServiceFacade;
import com.vntana.core.service.client.ClientOrganizationService;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:04 PM
 */
@Component
public class ClientOrganizationServiceFacadeImpl implements ClientOrganizationServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientOrganizationServiceFacadeImpl.class);

    private final ClientOrganizationService clientOrganizationService;

    public ClientOrganizationServiceFacadeImpl(final ClientOrganizationService clientOrganizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.clientOrganizationService = clientOrganizationService;
    }

    @Override
    public CheckAvailableClientOrganizationSlugResultResponse checkSlugAvailability(final CheckAvailableClientOrganizationSlugRequest request) {
        final Mutable<String> mutableSlug = new MutableObject<>(request.getSlug());
        final MutableInt mutableInt = new MutableInt(1);
        while (clientOrganizationService.findBySlug(mutableSlug.getValue()).isPresent()) {
            mutableSlug.setValue(format("%s%d", request.getSlug(), mutableInt.getAndIncrement()));
        }
        return new CheckAvailableClientOrganizationSlugResultResponse(mutableSlug.getValue().equals(request.getSlug()), mutableSlug.getValue());
    }
}
