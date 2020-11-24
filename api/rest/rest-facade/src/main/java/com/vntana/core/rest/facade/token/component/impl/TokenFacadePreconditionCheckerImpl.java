package com.vntana.core.rest.facade.token.component.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.token.AbstractToken;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest;
import com.vntana.core.model.token.request.CreateTokenInvitationUserToOrganizationRequest;
import com.vntana.core.model.token.request.CreateTokenUserInvitationToClientRequest;
import com.vntana.core.model.token.request.InvitationUuidAndTokenRequestModel;
import com.vntana.core.rest.facade.token.component.TokenFacadePreconditionChecker;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import com.vntana.core.service.invitation.user.InvitationUserToClientService;
import com.vntana.core.service.invitation.user.InvitationUserToOrganizationService;
import com.vntana.core.service.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.http.HttpStatus.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 11:45 AM
 */
@Component
public class TokenFacadePreconditionCheckerImpl implements TokenFacadePreconditionChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenFacadePreconditionCheckerImpl.class);

    private final TokenService tokenService;
    private final InvitationOrganizationService invitationOrganizationService;
    private final InvitationUserToOrganizationService invitationUserToOrganizationService;
    private final InvitationUserToClientService invitationUserToClientService;

    public TokenFacadePreconditionCheckerImpl(final TokenService tokenService,
                                              final InvitationOrganizationService invitationOrganizationService,
                                              final InvitationUserToClientService invitationUserToClientService,
                                              final InvitationUserToOrganizationService invitationUserToOrganizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.tokenService = tokenService;
        this.invitationOrganizationService = invitationOrganizationService;
        this.invitationUserToClientService = invitationUserToClientService;
        this.invitationUserToOrganizationService = invitationUserToOrganizationService;
    }

    @Override
    public SingleErrorWithStatus<TokenErrorResponseModel> checkCreateTokenInvitationOrganization(final CreateTokenInvitationOrganizationRequest request) {
        if (!invitationOrganizationService.existsByUuid(request.getInvitationOrganizationUuid())) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, TokenErrorResponseModel.INVITATION_ORGANIZATION_NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<TokenErrorResponseModel> checkCreateTokenUserInvitationToOrganization(final CreateTokenInvitationUserToOrganizationRequest request) {
        if (!invitationUserToOrganizationService.existsByUuid(request.getUserInvitationUuid())) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, TokenErrorResponseModel.INVITATION_USER_NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<TokenErrorResponseModel> checkCreateTokenUserInvitationToClient(final CreateTokenUserInvitationToClientRequest request) {
        if (request.getTokens().isEmpty()) {
            return SingleErrorWithStatus.of(SC_NOT_ACCEPTABLE, TokenErrorResponseModel.BAD_REQUEST);
        }
        final List<InvitationUuidAndTokenRequestModel> filtered = request.getTokens().stream()
                .filter(it -> StringUtils.isNotEmpty(it.getToken()))
                .filter(it -> StringUtils.isNotEmpty(it.getInvitationUuid()))
                .filter(it -> invitationUserToClientService.existsByUuid(it.getInvitationUuid()))
                .collect(Collectors.toList());
        if (request.getTokens().size() != filtered.size()) {
            return SingleErrorWithStatus.of(SC_NOT_ACCEPTABLE, TokenErrorResponseModel.BAD_REQUEST);
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
