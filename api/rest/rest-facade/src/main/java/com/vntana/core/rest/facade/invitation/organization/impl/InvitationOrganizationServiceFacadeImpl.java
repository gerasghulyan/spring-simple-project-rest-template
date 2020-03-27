package com.vntana.core.rest.facade.invitation.organization.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import com.vntana.core.model.invitation.organization.request.CreateInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.request.SendInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.response.CreateInvitationOrganizationResponse;
import com.vntana.core.model.invitation.organization.response.GetInvitationOrganizationResponse;
import com.vntana.core.model.invitation.organization.response.SendInvitationOrganizationResponse;
import com.vntana.core.model.invitation.organization.response.model.GetInvitationOrganizationResponseModel;
import com.vntana.core.rest.facade.invitation.organization.InvitationOrganizationFacadePreconditionChecker;
import com.vntana.core.rest.facade.invitation.organization.InvitationOrganizationServiceFacade;
import com.vntana.core.rest.facade.invitation.organization.component.InvitationOrganizationSenderComponent;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import com.vntana.core.service.invitation.organization.dto.CreateInvitationOrganizationDto;
import com.vntana.core.service.invitation.organization.mediator.InvitationOrganizationUuidAwareLifecycleMediator;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:05 PM
 */
@Component
public class InvitationOrganizationServiceFacadeImpl implements InvitationOrganizationServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationOrganizationServiceFacadeImpl.class);

    private final InvitationOrganizationService invitationOrganizationService;
    private final InvitationOrganizationFacadePreconditionChecker preconditionChecker;
    private final InvitationOrganizationUuidAwareLifecycleMediator invitationOrganizationUuidAwareLifecycleMediator;
    private final InvitationOrganizationSenderComponent invitationOrganizationSenderComponent;
    private final MapperFacade mapperFacade;

    public InvitationOrganizationServiceFacadeImpl(final InvitationOrganizationService invitationOrganizationService,
                                                   final InvitationOrganizationFacadePreconditionChecker preconditionChecker,
                                                   final InvitationOrganizationUuidAwareLifecycleMediator invitationOrganizationUuidAwareLifecycleMediator,
                                                   final InvitationOrganizationSenderComponent invitationOrganizationSenderComponent,
                                                   final MapperFacade mapperFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.invitationOrganizationService = invitationOrganizationService;
        this.preconditionChecker = preconditionChecker;
        this.invitationOrganizationUuidAwareLifecycleMediator = invitationOrganizationUuidAwareLifecycleMediator;
        this.invitationOrganizationSenderComponent = invitationOrganizationSenderComponent;
        this.mapperFacade = mapperFacade;
    }

    @Override
    public CreateInvitationOrganizationResponse create(final CreateInvitationOrganizationRequest request) {
        LOGGER.debug("Creating invitation organization for request- {}", request);
        final SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> error = preconditionChecker.checkCreateForPossibleErrors(request);
        if (error.isPresent()) {
            return new CreateInvitationOrganizationResponse(error.getHttpStatus(), error.getError());
        }
        final CreateInvitationOrganizationDto dto = new CreateInvitationOrganizationDto(
                request.getOwnerFullName(),
                request.getEmail(),
                request.getOrganizationName(),
                request.getSlug(),
                request.getCustomerSubscriptionDefinitionUuid()
        );
        final InvitationOrganization invitationOrganization = invitationOrganizationService.create(dto);
        invitationOrganizationUuidAwareLifecycleMediator.onCreated(invitationOrganization.getUuid());
        LOGGER.debug("Successfully created invitation organization for request- {}", request);
        return new CreateInvitationOrganizationResponse(invitationOrganization.getUuid());
    }

    @Override
    public GetInvitationOrganizationResponse getByUuid(final String uuid) {
        LOGGER.debug("Retrieving invitation organization having uuid - {}", uuid);
        final SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> error = preconditionChecker.checkGetByUuidForPossibleErrors(uuid);
        if (error.isPresent()) {
            return new GetInvitationOrganizationResponse(error.getHttpStatus(), error.getError());
        }
        final InvitationOrganization invitationOrganization = invitationOrganizationService.getByUuid(uuid);
        final GetInvitationOrganizationResponseModel responseModel = mapperFacade.map(invitationOrganization, GetInvitationOrganizationResponseModel.class);
        LOGGER.debug("Successfully retrieved invitation organization having uuid - {}", uuid);
        return new GetInvitationOrganizationResponse(responseModel);
    }

    @Override
    public SendInvitationOrganizationResponse sendInvitation(final SendInvitationOrganizationRequest request) {
        LOGGER.debug("Processing invitation organization facade sendInvitation for request - {}", request);
        final SendInvitationOrganizationResponse response = invitationOrganizationSenderComponent.sendInvitation(request);
        LOGGER.debug("Successfully processed invitation organization facade sendInvitation for request - {}", request);
        return response;
    }
}
