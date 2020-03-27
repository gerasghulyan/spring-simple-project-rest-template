package com.vntana.core.rest.facade.token.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.token.AbstractToken;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest;
import com.vntana.core.rest.facade.token.TokeFacadePreconditionChecker;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import com.vntana.core.service.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 11:45 AM
 */
@Component
public class TokeFacadePreconditionCheckerImpl implements TokeFacadePreconditionChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokeFacadePreconditionCheckerImpl.class);

    private final TokenService tokenService;
    private final InvitationOrganizationService invitationOrganizationService;

    public TokeFacadePreconditionCheckerImpl(final TokenService tokenService, final InvitationOrganizationService invitationOrganizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.tokenService = tokenService;
        this.invitationOrganizationService = invitationOrganizationService;
    }

    @Override
    public SingleErrorWithStatus<TokenErrorResponseModel> checkCreateTokenInvitationOrganization(final CreateTokenInvitationOrganizationRequest request) {
        if (!invitationOrganizationService.existsByUuid(request.getInvitationOrganizationUuid())) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, TokenErrorResponseModel.INVITATION_ORGANIZATION_NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<TokenErrorResponseModel> checkIsExpired(final String token) {
        if (StringUtils.isEmpty(token)) {
            return SingleErrorWithStatus.of(SC_UNPROCESSABLE_ENTITY, TokenErrorResponseModel.MISSING_TOKEN);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<TokenErrorResponseModel> checkExpire(final String token) {
        if (StringUtils.isEmpty(token)) {
            return SingleErrorWithStatus.of(SC_UNPROCESSABLE_ENTITY, TokenErrorResponseModel.MISSING_TOKEN);
        }
        final Optional<AbstractToken> tokenOptional = tokenService.findByToken(token);
        if (!tokenOptional.isPresent()) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, TokenErrorResponseModel.TOKEN_NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
    }
}
