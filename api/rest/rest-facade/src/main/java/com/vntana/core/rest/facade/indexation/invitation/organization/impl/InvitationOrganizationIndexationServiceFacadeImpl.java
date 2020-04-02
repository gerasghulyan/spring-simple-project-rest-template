package com.vntana.core.rest.facade.indexation.invitation.organization.impl;

import com.vntana.commons.api.model.response.indexation.IndexationResultResponse;
import com.vntana.commons.api.model.response.indexation.error.IndexationErrorResponseModel;
import com.vntana.core.rest.facade.indexation.invitation.organization.InvitationOrganizationIndexationServiceFacade;
import com.vntana.core.rest.facade.indexation.invitation.organization.component.InvitationOrganizationIndexationComponent;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import com.vntana.core.service.invitation.organization.dto.GetAllInvitationOrganizationsDto;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;

/**
 * Created by Arman Gevorgyan.
 * Date: 4/2/20
 * Time: 10:45 AM
 */
@Component
public class InvitationOrganizationIndexationServiceFacadeImpl implements InvitationOrganizationIndexationServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationOrganizationIndexationServiceFacadeImpl.class);

    private final InvitationOrganizationService invitationOrganizationService;
    private final InvitationOrganizationIndexationComponent invitationOrganizationIndexationComponent;

    public InvitationOrganizationIndexationServiceFacadeImpl(final InvitationOrganizationService invitationOrganizationService, final InvitationOrganizationIndexationComponent invitationOrganizationIndexationComponent) {
        this.invitationOrganizationService = invitationOrganizationService;
        this.invitationOrganizationIndexationComponent = invitationOrganizationIndexationComponent;
    }

    @Transactional
    @Override
    public IndexationResultResponse reindexAll() {
        LOGGER.debug("Processing the re-indexation of all organization invitations");
        final long startTime = System.currentTimeMillis();
        final CompletableFuture<Void> fullReIndexationFuture = CompletableFuture.supplyAsync(() -> invitationOrganizationService.getAll(new GetAllInvitationOrganizationsDto(Integer.MAX_VALUE)))
                .thenCompose(invitations -> Stream.ofAll(invitations.getContent())
                        .map(invitation -> CompletableFuture
                                .runAsync(() -> invitationOrganizationIndexationComponent.indexByOne(invitation.getUuid()))
                                .handle((aVoid, throwable) -> {
                                    if (Objects.nonNull(throwable)) {
                                        LOGGER.error("Error occurs while trying to reindex the organization invitation having uuid - {} with exception - {}", invitation.getUuid(), throwable);
                                        throw new IllegalStateException(format("Error occurs while trying to reindex the organization invitation having uuid - %s", invitation.getUuid()), throwable);
                                    }
                                    LOGGER.info("Successfully reindex the organization having uuid - {}", invitation.getUuid());
                                    return aVoid;
                                })
                        )
                        .reduce((v1, v2) -> v1.thenCombine(v2, (aVoid, aVoid2) -> aVoid2)));
        return Try
                .of(fullReIndexationFuture::get)
                .map(aVoid -> {
                            LOGGER.debug("Successfully processed the re-indexation of all organization invitation which took millis - {}", System.currentTimeMillis() - startTime);
                            return new IndexationResultResponse(System.currentTimeMillis() - startTime);
                        }
                ).getOrElseGet(throwable -> new IndexationResultResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, IndexationErrorResponseModel.INDEXATION_ERROR));
    }
}
