package com.vntana.core.rest.facade.indexation.organization.impl;

import com.vntana.commons.api.model.response.indexation.IndexationResultResponse;
import com.vntana.commons.api.model.response.indexation.error.IndexationErrorResponseModel;
import com.vntana.core.rest.facade.indexation.organization.OrganizationIndexationServiceFacade;
import com.vntana.core.rest.facade.indexation.organization.component.OrganizationIndexationComponent;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.organization.dto.GetAllOrganizationDto;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;

/**
 * Created by Manuk Gharslyan.
 * Date: 3/24/2020
 * Time: 5:37 PM
 */
@Component
public class OrganizationIndexationServiceFacadeImpl implements OrganizationIndexationServiceFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationIndexationServiceFacadeImpl.class);

    private final OrganizationService organizationService;
    private final OrganizationIndexationComponent organizationIndexationComponent;

    public OrganizationIndexationServiceFacadeImpl(final OrganizationService organizationService,
                                                   final OrganizationIndexationComponent organizationIndexationComponent) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationService = organizationService;
        this.organizationIndexationComponent = organizationIndexationComponent;
    }

    @Override
    public IndexationResultResponse reindexAll() {
        LOGGER.debug("Processing the re-indexation of all organizations");
        final long startTime = System.currentTimeMillis();
        final CompletableFuture<Void> fullReIndexationFuture = CompletableFuture.supplyAsync(() -> organizationService.getAll(new GetAllOrganizationDto(organizationService.count().intValue())))
                .thenCompose(organizations -> Stream.ofAll(organizations.getContent())
                        .map(theOrganization -> CompletableFuture
                                .runAsync(() -> organizationIndexationComponent.indexByOne(theOrganization.getUuid()))
                                .handle((aVoid, throwable) -> {
                                    if (Objects.nonNull(throwable)) {
                                        LOGGER.error("Error occurs while trying to reindex the organization having uuid - {} with exception - {}", theOrganization.getUuid(), throwable);
                                        throw new IllegalStateException(format("Error occurs while trying to reindex the organization having uuid - %s", theOrganization.getUuid()), throwable);
                                    }
                                    LOGGER.info("Successfully reindex the organization having uuid - {}", theOrganization.getUuid());
                                    return aVoid;
                                })
                        )
                        .reduce((v1, v2) -> v1.thenCombine(v2, (aVoid, aVoid2) -> aVoid2)));

        LOGGER.debug("Successfully processed the re-indexation of all organizations which took millis - {}", System.currentTimeMillis() - startTime);
        return Try
                .of(fullReIndexationFuture::get)
                .map(aVoid -> new IndexationResultResponse(System.currentTimeMillis() - startTime))
                .getOrElseGet(throwable -> new IndexationResultResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, IndexationErrorResponseModel.INDEXATION_ERROR));
    }
}