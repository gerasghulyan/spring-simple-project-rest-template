package com.vntana.core.rest.facade.invitation.organization.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.token.TokenInvitationOrganization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import com.vntana.core.model.invitation.organization.request.*;
import com.vntana.core.model.invitation.organization.response.*;
import com.vntana.core.model.invitation.organization.response.model.GetInvitationOrganizationResponseModel;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.invitation.organization.InvitationOrganizationServiceFacade;
import com.vntana.core.rest.facade.invitation.organization.checker.InvitationOrganizationFacadePreconditionChecker;
import com.vntana.core.rest.facade.invitation.organization.component.InvitationOrganizationSenderComponent;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import com.vntana.core.service.invitation.organization.dto.CreateInvitationOrganizationDto;
import com.vntana.core.service.invitation.organization.dto.UpdateInvitationOrganizationStatusDto;
import com.vntana.core.service.invitation.organization.mediator.InvitationOrganizationUuidAwareLifecycleMediator;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.organization.dto.CreateOrganizationFromInvitationDto;
import com.vntana.core.service.organization.mediator.OrganizationLifecycleMediator;
import com.vntana.core.service.organization.mediator.OrganizationUuidAwareLifecycleMediator;
import com.vntana.core.service.token.TokenService;
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.CreateUserDto;
import com.vntana.core.service.user.role.UserRoleService;
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:05 PM
 */
@Component
public class InvitationOrganizationServiceFacadeImpl implements InvitationOrganizationServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationOrganizationServiceFacadeImpl.class);

    private final InvitationOrganizationService invitationOrganizationService;
    private final TokenService tokenService;
    private final InvitationOrganizationFacadePreconditionChecker preconditionChecker;
    private final InvitationOrganizationUuidAwareLifecycleMediator invitationOrganizationUuidAwareLifecycleMediator;
    private final InvitationOrganizationSenderComponent invitationOrganizationSenderComponent;
    private final MapperFacade mapperFacade;
    private final OrganizationService organizationService;
    private final PersistenceUtilityService persistenceUtilityService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final OrganizationLifecycleMediator organizationLifecycleMediator;
    private final OrganizationUuidAwareLifecycleMediator organizationUuidAwareLifecycleMediator;
    private final TokenInvitationOrganizationService tokenInvitationOrganizationService;

    public InvitationOrganizationServiceFacadeImpl(final InvitationOrganizationService invitationOrganizationService,
                                                   final TokenService tokenService,
                                                   final InvitationOrganizationFacadePreconditionChecker preconditionChecker,
                                                   final InvitationOrganizationUuidAwareLifecycleMediator invitationOrganizationUuidAwareLifecycleMediator,
                                                   final InvitationOrganizationSenderComponent invitationOrganizationSenderComponent,
                                                   final MapperFacade mapperFacade,
                                                   final OrganizationService organizationService,
                                                   final PersistenceUtilityService persistenceUtilityService,
                                                   final UserService userService,
                                                   final UserRoleService userRoleService,
                                                   final OrganizationLifecycleMediator organizationLifecycleMediator,
                                                   final OrganizationUuidAwareLifecycleMediator organizationUuidAwareLifecycleMediator,
                                                   final TokenInvitationOrganizationService tokenInvitationOrganizationService) {
        this.tokenService = tokenService;
        this.invitationOrganizationService = invitationOrganizationService;
        this.preconditionChecker = preconditionChecker;
        this.invitationOrganizationUuidAwareLifecycleMediator = invitationOrganizationUuidAwareLifecycleMediator;
        this.invitationOrganizationSenderComponent = invitationOrganizationSenderComponent;
        this.mapperFacade = mapperFacade;
        this.organizationService = organizationService;
        this.persistenceUtilityService = persistenceUtilityService;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.organizationLifecycleMediator = organizationLifecycleMediator;
        this.organizationUuidAwareLifecycleMediator = organizationUuidAwareLifecycleMediator;
        this.tokenInvitationOrganizationService = tokenInvitationOrganizationService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
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

    @Override
    public UpdateInvitationOrganizationStatusResponse updateStatus(final UpdateInvitationOrganizationStatusRequest request) {
        LOGGER.debug("Processing invitation organization facade updateStatus for request - {}", request);
        final SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> singleErrorWithStatus = preconditionChecker.checkGetByUuidForPossibleErrors(request.getUuid());
        if (singleErrorWithStatus.isPresent()) {
            return new UpdateInvitationOrganizationStatusResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        final InvitationOrganization response = invitationOrganizationService.updateStatus(mapperFacade.map(request, UpdateInvitationOrganizationStatusDto.class));
        LOGGER.debug("Successfully processed invitation organization facade updateStatus for request - {}", request);
        return new UpdateInvitationOrganizationStatusResponse(response.getUuid());
    }

    @Transactional
    @Override
    public RejectInvitationOrganizationResponse reject(final RejectInvitationOrganizationRequest request) {
        LOGGER.debug("Processing invitation organization facade reject for request - {}", request);
        final SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> singleErrorWithStatus = preconditionChecker.checkRejectInvitationForPossibleErrors(request);
        if (singleErrorWithStatus.isPresent()) {
            return new RejectInvitationOrganizationResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        final TokenInvitationOrganization tokenInvitationOrganization = tokenInvitationOrganizationService.getByToken(request.getToken());
        final InvitationOrganization invitation = tokenInvitationOrganization.getInvitationOrganization();
        final InvitationOrganization rejectedInvitationOrganization = invitationOrganizationService.updateStatus(new UpdateInvitationOrganizationStatusDto(
                invitation.getUuid(),
                InvitationStatus.REJECTED
        ));
        tokenService.findByTokenAndExpire(request.getToken());
        invitationOrganizationUuidAwareLifecycleMediator.onUpdated(rejectedInvitationOrganization.getUuid());
        LOGGER.debug("Successfully processed invitation organization facade reject for request - {}", request);
        return new RejectInvitationOrganizationResponse();
    }

    @Override
    public AcceptInvitationOrganizationResponse accept(final AcceptInvitationOrganizationRequest request) {
        LOGGER.debug("Processing invitation organization facade accept for request - {}", request);
        final SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> singleErrorWithStatus = checkAcceptErrorWithStatus(request);
        if (singleErrorWithStatus.isPresent()) {
            return new AcceptInvitationOrganizationResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        final InvitationOrganization invitation = getInvitationOrganizationFromToken(request.getToken());
        final SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> errorForUser = preconditionChecker.checkAcceptInvitationWhenUserExistsForPossibleErrors(invitation.getEmail());
        if (errorForUser.isPresent()) {
            return new AcceptInvitationOrganizationResponse(errorForUser.getHttpStatus(), errorForUser.getError());
        }
        final User user = userService.getByEmail(invitation.getEmail());
        final Mutable<String> mutableResponse = new MutableObject<>();
        persistenceUtilityService.runInNewTransaction(() -> {
            final Organization organization = createOrganizationWithInvitation(invitation, request.getOrganizationName(), request.getOrganizationSlug());
            userRoleService.grantOrganizationOwnerRole(new UserGrantOrganizationRoleDto(
                    user.getUuid(),
                    organization.getUuid())
            );
            afterOrganizationCreatedInTransaction(request.getToken(), invitation, mutableResponse, organization);
        });
        return new AcceptInvitationOrganizationResponse(mutableResponse.getValue());
    }

    @Override
    public AcceptInvitationOrganizationResponse acceptAndSignUp(final AcceptAndSignUpInvitationOrganizationRequest request) {
        LOGGER.debug("Processing invitation organization facade acceptAndSignUp for request - {}", request);
        final AcceptInvitationOrganizationRequest acceptRequest = new AcceptInvitationOrganizationRequest(
                request.getToken(),
                request.getOrganizationName(),
                request.getOrganizationSlug()
        );
        final SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> singleErrorWithStatus = checkAcceptErrorWithStatus(acceptRequest);
        if (singleErrorWithStatus.isPresent()) {
            return new AcceptInvitationOrganizationResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        final InvitationOrganization invitation = getInvitationOrganizationFromToken(request.getToken());
        final SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> errorForUser = preconditionChecker.checkAcceptInvitationWhenUserNotExistsForPossibleErrors(invitation.getEmail());
        if (errorForUser.isPresent()) {
            return new AcceptInvitationOrganizationResponse(errorForUser.getHttpStatus(), errorForUser.getError());
        }
        final Mutable<String> mutableResponse = new MutableObject<>();
        persistenceUtilityService.runInNewTransaction(() -> {
            final Organization organization = createOrganizationWithInvitation(invitation, request.getOrganizationName(), request.getOrganizationSlug());
            final User user = userService.create(new CreateUserDto(
                    request.getUserFullName(),
                    invitation.getEmail(),
                    request.getUserPassword(),
                    organization.getUuid(),
                    UserRole.ORGANIZATION_OWNER
            ));
            userService.makeVerified(user.getEmail());
            afterOrganizationCreatedInTransaction(request.getToken(), invitation, mutableResponse, organization);
        });
        return new AcceptInvitationOrganizationResponse(mutableResponse.getValue());
    }

    //region Utility methods
    private void afterOrganizationCreatedInTransaction(
            final String token,
            final InvitationOrganization invitation,
            final Mutable<String> mutableResponse,
            final Organization organization) {
        mutableResponse.setValue(organization.getUuid());
        tokenService.findByTokenAndExpire(token);
        invitationOrganizationService.updateStatus(new UpdateInvitationOrganizationStatusDto(invitation.getUuid(), InvitationStatus.ACCEPTED));
        organizationLifecycleMediator.onCreated(organization);
        organizationUuidAwareLifecycleMediator.onCreated(organization.getUuid());
        invitationOrganizationUuidAwareLifecycleMediator.onUpdated(invitation.getUuid());
    }

    private InvitationOrganization getInvitationOrganizationFromToken(final String token) {
        final TokenInvitationOrganization tokenInvitationOrganization = tokenInvitationOrganizationService.getByToken(token);
        return tokenInvitationOrganization.getInvitationOrganization();
    }

    private Organization createOrganizationWithInvitation(final InvitationOrganization invitation, final String name, final String slug) {
        LOGGER.debug("Creating organization for invitation name - {} nad slug - {}", name, slug);
        return organizationService.createWithInvitation(new CreateOrganizationFromInvitationDto(
                name,
                slug,
                invitation.getUuid()
        ));
    }

    private SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> checkAcceptErrorWithStatus(final AcceptInvitationOrganizationRequest request) {
        return preconditionChecker.checkAcceptInvitationForPossibleErrors(request);
    }
    //endregion
}
