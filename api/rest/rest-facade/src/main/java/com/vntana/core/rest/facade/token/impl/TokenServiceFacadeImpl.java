package com.vntana.core.rest.facade.token.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.token.AbstractToken;
import com.vntana.core.domain.token.TokenInvitationOrganization;
import com.vntana.core.domain.token.TokenInvitationUser;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest;
import com.vntana.core.model.token.request.CreateTokenInvitationUserRequest;
import com.vntana.core.model.token.response.TokenCreateResultResponse;
import com.vntana.core.model.token.response.TokenExpireResultResponse;
import com.vntana.core.model.token.response.TokenIsExpiredResultResponse;
import com.vntana.core.rest.facade.token.TokenServiceFacade;
import com.vntana.core.rest.facade.token.component.TokenFacadePreconditionChecker;
import com.vntana.core.service.token.TokenService;
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService;
import com.vntana.core.service.token.invitation.organization.dto.CreateTokenInvitationOrganizationDto;
import com.vntana.core.service.token.invitation.user.TokenInvitationUserService;
import com.vntana.core.service.token.invitation.user.dto.CreateTokenInvitationUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:50 PM
 */
@Component
public class TokenServiceFacadeImpl implements TokenServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceFacadeImpl.class);

    private final TokenService tokenService;
    private final TokenInvitationOrganizationService tokenInvitationOrganizationService;
    private final TokenInvitationUserService tokenInvitationUserService;
    private final TokenFacadePreconditionChecker preconditionChecker;

    public TokenServiceFacadeImpl(final TokenService tokenService,
                                  final TokenInvitationOrganizationService tokenInvitationOrganizationService,
                                  final TokenInvitationUserService tokenInvitationUserService,
                                  final TokenFacadePreconditionChecker preconditionChecker) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.tokenService = tokenService;
        this.tokenInvitationOrganizationService = tokenInvitationOrganizationService;
        this.tokenInvitationUserService = tokenInvitationUserService;
        this.preconditionChecker = preconditionChecker;
    }

    @Override
    public TokenCreateResultResponse createTokenInvitationOrganization(final CreateTokenInvitationOrganizationRequest request) {
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

    @Override
    public TokenCreateResultResponse createTokenInvitationUser(final CreateTokenInvitationUserRequest request) {
        LOGGER.debug("Processing token facade createTokenInvitationUser for request - {}", request);
        final SingleErrorWithStatus<TokenErrorResponseModel> error = preconditionChecker.checkCreateTokenInvitationUser(request);
        if (error.isPresent()) {
            return new TokenCreateResultResponse(error.getHttpStatus(), error.getError());
        }
        final CreateTokenInvitationUserDto dto = new CreateTokenInvitationUserDto(
                request.getToken(),
                request.getInvitationUserUuid()
        );
        final TokenInvitationUser tokenInvitationUser = tokenInvitationUserService.create(dto);
        LOGGER.debug("Successfully processed token facade createTokenInvitationUser for request - {}", request);
        return new TokenCreateResultResponse(tokenInvitationUser.getUuid());
    }

    @Override
    public TokenIsExpiredResultResponse isExpired(final String token) {
        final SingleErrorWithStatus<TokenErrorResponseModel> error = preconditionChecker.checkIsExpired(token);
        if (error.isPresent()) {
            return new TokenIsExpiredResultResponse(error.getHttpStatus(), error.getError());
        }
        return tokenService.findByToken(token)
                .map(AbstractToken::isExpired)
                .map(TokenIsExpiredResultResponse::new)
                .orElse(new TokenIsExpiredResultResponse(SC_NOT_FOUND, TokenErrorResponseModel.TOKEN_NOT_FOUND));
    }

    @Override
    public TokenExpireResultResponse expire(final String token) {
        LOGGER.debug("Processing token facade expire");
        final SingleErrorWithStatus<TokenErrorResponseModel> error = preconditionChecker.checkExpire(token);
        if (error.isPresent()) {
            return new TokenExpireResultResponse(error.getHttpStatus(), error.getError());
        }
        tokenService.findByToken(token).ifPresent(abstractToken -> tokenService.expire(abstractToken.getUuid()));
        LOGGER.debug("Successfully processed token facade expire");
        return new TokenExpireResultResponse();
    }
}
