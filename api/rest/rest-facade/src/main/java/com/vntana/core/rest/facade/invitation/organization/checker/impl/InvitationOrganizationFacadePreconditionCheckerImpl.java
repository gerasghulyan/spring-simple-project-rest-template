package com.vntana.core.rest.facade.invitation.organization.checker.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.token.TokenInvitationOrganization;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import com.vntana.core.model.invitation.organization.request.AcceptInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.request.CreateInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.request.RejectInvitationOrganizationRequest;
import com.vntana.core.rest.facade.invitation.organization.checker.InvitationOrganizationFacadePreconditionChecker;
import com.vntana.core.service.common.component.SlugValidationComponent;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService;
import com.vntana.core.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 2:28 PM
 */
@Component
public class InvitationOrganizationFacadePreconditionCheckerImpl implements InvitationOrganizationFacadePreconditionChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationOrganizationFacadePreconditionCheckerImpl.class);

    private final InvitationOrganizationService invitationOrganizationService;
    private final SlugValidationComponent slugValidationComponent;
    private final OrganizationService organizationService;
    private final UserService userService;
    private final TokenInvitationOrganizationService tokenInvitationOrganizationService;

    public InvitationOrganizationFacadePreconditionCheckerImpl(final InvitationOrganizationService invitationOrganizationService,
                                                               final SlugValidationComponent slugValidationComponent,
                                                               final OrganizationService organizationService,
                                                               final UserService userService,
                                                               final TokenInvitationOrganizationService tokenInvitationOrganizationService) {
        this.tokenInvitationOrganizationService = tokenInvitationOrganizationService;
        this.invitationOrganizationService = invitationOrganizationService;
        this.slugValidationComponent = slugValidationComponent;
        this.organizationService = organizationService;
        this.userService = userService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> checkCreateForPossibleErrors(final CreateInvitationOrganizationRequest request) {
        LOGGER.debug("Checking invitation organization creation precondition for request - {}", request);
        final String slug = request.getSlug();
        if (!slugValidationComponent.validate(slug)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_PRECONDITION_FAILED, InvitationOrganizationErrorResponseModel.INVALID_SLUG);
        }
        if (organizationService.findBySlug(slug).isPresent()) {
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationOrganizationErrorResponseModel.SLUG_IS_NOT_AVAILABLE);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> checkGetByUuidForPossibleErrors(final String uuid) {
        LOGGER.debug("Checking invitation organization retrieve by uuid precondition for uuid - {}", uuid);
        if (StringUtils.isEmpty(uuid)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_UNPROCESSABLE_ENTITY, InvitationOrganizationErrorResponseModel.MISSING_UUID);
        }
        if (!invitationOrganizationService.existsByUuid(uuid)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationOrganizationErrorResponseModel.NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> checkRejectInvitationForPossibleErrors(final RejectInvitationOrganizationRequest request) {
        LOGGER.debug("Checking reject invitation organization precondition for request - {}", request);
        final Optional<TokenInvitationOrganization> tokenInvitationOrganizationOptional = tokenInvitationOrganizationService.findByToken(request.getToken());
        if (!tokenInvitationOrganizationOptional.isPresent()) {
            LOGGER.debug("Can not find TokenInvitationOrganization by token - {}", request.getToken());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationOrganizationErrorResponseModel.TOKEN_NOT_FOUND);
        }
        final TokenInvitationOrganization tokenInvitationOrganization = tokenInvitationOrganizationOptional.get();
        final InvitationOrganization invitation =  tokenInvitationOrganization.getInvitationOrganization();
        final SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> statusErrors = validateTokenStatus(invitation);
        if (statusErrors.isPresent()) {
            return statusErrors;
        }
        if (tokenInvitationOrganization.isExpired()) {
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationOrganizationErrorResponseModel.TOKEN_IS_EXPIRED);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> checkAcceptInvitationForPossibleErrors(final AcceptInvitationOrganizationRequest request) {
        LOGGER.debug("Checking accept invitation organization precondition for request - {}", request);
        final Optional<TokenInvitationOrganization> tokenInvitationOrganizationOptional = tokenInvitationOrganizationService.findByToken(request.getToken());
        if (!tokenInvitationOrganizationOptional.isPresent()) {
            LOGGER.debug("Can not find TokenInvitationOrganization by token - {}", request.getToken());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationOrganizationErrorResponseModel.TOKEN_NOT_FOUND);
        }
        final TokenInvitationOrganization tokenInvitationOrganization = tokenInvitationOrganizationOptional.get();
        final InvitationOrganization invitation =  tokenInvitationOrganization.getInvitationOrganization();
        final SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> statusErrors = validateTokenStatus(invitation);
        if (statusErrors.isPresent()) {
            return statusErrors;
        }
        if (tokenInvitationOrganization.isExpired()) {
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationOrganizationErrorResponseModel.TOKEN_IS_EXPIRED);
        }
        final Optional<Organization> organizationOptional = organizationService.findBySlug(request.getOrganizationSlug());
        if (organizationOptional.isPresent()) {
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationOrganizationErrorResponseModel.SLUG_IS_NOT_AVAILABLE);
        }
        final Optional<User> userOptional = userService.findByEmail(invitation.getEmail());
        if (!userOptional.isPresent()) {
            LOGGER.debug("Can not find User by email from Invitation - {}", invitation.getEmail());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationOrganizationErrorResponseModel.USER_NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
    }

    //region Utility Methods
    private SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> validateTokenStatus(final InvitationOrganization invitation) {
        if (invitation.getStatus().equals(InvitationStatus.REJECTED)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationOrganizationErrorResponseModel.ALREADY_REJECTED_INVITATION);
        }
        if (invitation.getStatus().equals(InvitationStatus.ACCEPTED)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationOrganizationErrorResponseModel.ALREADY_ACCEPTED_INVITATION);
        }
        return SingleErrorWithStatus.empty();
    }
    //endregion
}
