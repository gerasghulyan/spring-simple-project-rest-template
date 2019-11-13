package com.vntana.core.rest.facade.client.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest;
import com.vntana.core.model.client.request.CreateClientOrganizationRequest;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse;
import com.vntana.core.model.client.response.CreateClientOrganizationResultResponse;
import com.vntana.core.rest.facade.client.ClientOrganizationServiceFacade;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.client.dto.CreateClientOrganizationDto;
import com.vntana.core.service.organization.OrganizationService;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static java.lang.String.format;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:04 PM
 */
@Component
public class ClientOrganizationServiceFacadeImpl implements ClientOrganizationServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientOrganizationServiceFacadeImpl.class);

    private final MapperFacade mapperFacade;
    private final ClientOrganizationService clientOrganizationService;
    private final OrganizationService organizationService;

    public ClientOrganizationServiceFacadeImpl(final MapperFacade mapperFacade, final ClientOrganizationService clientOrganizationService, final OrganizationService organizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.mapperFacade = mapperFacade;
        this.clientOrganizationService = clientOrganizationService;
        this.organizationService = organizationService;
    }

    @Override
    public CheckAvailableClientOrganizationSlugResultResponse checkSlugAvailability(final CheckAvailableClientOrganizationSlugRequest request) {
        final Mutable<String> mutableSlug = new MutableObject<>(request.getSlug());
        final MutableInt mutableInt = new MutableInt(1);
        while (clientOrganizationService.findBySlugAndOrganization(mutableSlug.getValue(), request.getOrganizationUuid()).isPresent()) {
            LOGGER.debug("Client organization with slug - {} and organization - {} already exists, trying to generate suggested one",
                    mutableSlug.getValue(), request.getOrganizationUuid());
            mutableSlug.setValue(format("%s%d", request.getSlug(), mutableInt.getAndIncrement()));
        }
        return new CheckAvailableClientOrganizationSlugResultResponse(mutableSlug.getValue().equals(request.getSlug()), mutableSlug.getValue());
    }

    @Override
    public CreateClientOrganizationResultResponse create(final CreateClientOrganizationRequest request) {
        if (!organizationService.findByUuid(request.getOrganizationUuid()).isPresent()) {
            return new CreateClientOrganizationResultResponse(Collections.singletonList(ClientOrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND));
        }
        return clientOrganizationService.findBySlugAndOrganization(request.getSlug(), request.getOrganizationUuid())
                .map(clientOrganization -> {
                    LOGGER.debug("Client organization already exists for slug - {}", request.getSlug());
                    return new CreateClientOrganizationResultResponse(Collections.singletonList(ClientOrganizationErrorResponseModel.SLUG_ALREADY_EXISTS));
                })
                .orElseGet(() -> {
                    LOGGER.debug("Creating client organization for request - {}", request);
                    final CreateClientOrganizationDto dto = mapperFacade.map(request, CreateClientOrganizationDto.class);
                    final ClientOrganization clientOrganization = clientOrganizationService.create(dto);
                    return new CreateClientOrganizationResultResponse(clientOrganization.getUuid());
                });
    }
}
