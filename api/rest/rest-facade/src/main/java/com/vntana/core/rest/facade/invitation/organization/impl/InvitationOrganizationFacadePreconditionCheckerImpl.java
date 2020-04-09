package com.vntana.core.rest.facade.invitation.organization.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import com.vntana.core.model.invitation.organization.request.CreateInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.request.RejectInvitationOrganizationRequest;
import com.vntana.core.rest.facade.invitation.organization.InvitationOrganizationFacadePreconditionChecker;
import com.vntana.core.service.common.component.SlugValidationComponent;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 2:28 PM
 */
@Component
public class InvitationOrganizationFacadePreconditionCheckerImpl implements InvitationOrganizationFacadePreconditionChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationOrganizationFacadePreconditionCheckerImpl.class);

    private final InvitationOrganizationService invitationOrganizationService;
    private final TokenService tokenService;
    private final SlugValidationComponent slugValidationComponent;
    private final OrganizationService organizationService;

    public InvitationOrganizationFacadePreconditionCheckerImpl(final InvitationOrganizationService invitationOrganizationService,
                                                               final TokenService tokenService,
                                                               final SlugValidationComponent slugValidationComponent,
                                                               final OrganizationService organizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.invitationOrganizationService = invitationOrganizationService;
        this.tokenService = tokenService;
        this.slugValidationComponent = slugValidationComponent;
        this.organizationService = organizationService;
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
        LOGGER.debug("Checking reject invitation organization precondition for uuid - {}", request.getUuid());
        if (!tokenService.existsByToken(request.getToken())) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationOrganizationErrorResponseModel.TOKEN_NOT_FOUND);
        }
        if (!invitationOrganizationService.existsByUuid(request.getUuid())) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationOrganizationErrorResponseModel.NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
    }
}
