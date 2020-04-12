package com.vntana.core.rest.facade.token.invitation.organization.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.token.TokenInvitationOrganization;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest;
import com.vntana.core.model.token.response.TokenCreateResultResponse;
import com.vntana.core.rest.facade.token.TokenFacadePreconditionChecker;
import com.vntana.core.rest.facade.token.invitation.organization.TokenInvitationOrganizationServiceFacade;
import com.vntana.core.service.token.dto.CreateTokenInvitationOrganizationDto;
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Geras Ghulyan
 * Date: 3/23/20
 * Time: 2:50 PM
 */
@Component
public class TokenInvitationOrganizationServiceFacadeImpl implements TokenInvitationOrganizationServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenInvitationOrganizationServiceFacadeImpl.class);

    private final TokenInvitationOrganizationService tokenInvitationOrganizationService;
    private final TokenFacadePreconditionChecker preconditionChecker;

    public TokenInvitationOrganizationServiceFacadeImpl(final TokenInvitationOrganizationService tokenInvitationOrganizationService, final TokenFacadePreconditionChecker preconditionChecker) {
        this.tokenInvitationOrganizationService = tokenInvitationOrganizationService;
        this.preconditionChecker = preconditionChecker;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public TokenCreateResultResponse create(final CreateTokenInvitationOrganizationRequest request) {
        LOGGER.debug("Processing token facade createTokenInvitationOrganization for request - {}", request);
        final SingleErrorWithStatus<TokenErrorResponseModel> error = preconditionChecker.checkCreateTokenInvitationOrganization(request);
        if (error.isPresent()) {
            return new TokenCreateResultResponse(error.getHttpStatus(), error.getError());
        }
        final CreateTokenInvitationOrganizationDto dto = new CreateTokenInvitationOrganizationDto(
                request.getToken(),
                request.getInvitationOrganizationUuid()
        );
        final TokenInvitationOrganization tokenInvitationOrganization = tokenInvitationOrganizationService.create(dto);
        LOGGER.debug("Successfully processed token facade createTokenInvitationOrganization for request - {}", request);
        return new TokenCreateResultResponse(tokenInvitationOrganization.getUuid());
    }
}
