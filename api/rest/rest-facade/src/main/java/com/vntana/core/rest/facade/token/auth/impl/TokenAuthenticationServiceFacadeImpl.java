package com.vntana.core.rest.facade.token.auth.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.token.TokenAuthentication;
import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistRequest;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithClientOrganizationRequest;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithOrganizationRequest;
import com.vntana.core.model.token.auth.response.*;
import com.vntana.core.model.token.auth.response.model.FindTokenAuthenticationByTokenResponseModel;
import com.vntana.core.rest.facade.token.auth.TokenAuthenticationServiceFacade;
import com.vntana.core.rest.facade.token.auth.component.precondition.TokenAuthenticationServiceFacadePreconditionCheckerComponent;
import com.vntana.core.service.token.auth.TokenAuthenticationService;
import com.vntana.core.service.token.auth.dto.CreateTokenAuthenticationDto;
import com.vntana.core.service.token.auth.dto.CreateTokenAuthenticationWithClientOrganizationDto;
import com.vntana.core.service.token.auth.dto.CreateTokenAuthenticationWithOrganizationDto;
import com.vntana.core.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:25 AM
 */
@Component
public class TokenAuthenticationServiceFacadeImpl implements TokenAuthenticationServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationServiceFacadeImpl.class);

    private final TokenAuthenticationService tokenAuthenticationService;
    private final UserService userService;
    private final TokenAuthenticationServiceFacadePreconditionCheckerComponent preconditionCheckerComponent;

    public TokenAuthenticationServiceFacadeImpl(
            final TokenAuthenticationService tokenAuthenticationService,
            final UserService userService,
            final TokenAuthenticationServiceFacadePreconditionCheckerComponent preconditionCheckerComponent) {
        this.preconditionCheckerComponent = preconditionCheckerComponent;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userService = userService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public PersistTokenAuthenticationResultResponse persist(final TokenAuthenticationPersistRequest request) {
        LOGGER.debug("Persisting token of user having uuid - {}", request.getToken());
        if (!userService.existsByUuid(request.getUserUuid())) {
            return new PersistTokenAuthenticationResultResponse(SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.USER_NOT_FOUND);
        }
        tokenAuthenticationService.create(new CreateTokenAuthenticationDto(request.getUserUuid(), request.getToken(), request.getExpiration()));
        LOGGER.debug("Successfully persisted token of user having uuid - {}", request.getToken());
        return new PersistTokenAuthenticationResultResponse();
    }

    @Override
    public PersistTokenAuthenticationResultResponse persistWithOrganization(final TokenAuthenticationPersistWithOrganizationRequest request) {
        LOGGER.debug("Persisting organization token of user having uuid - {} and organization uuid - {}", request.getUserUuid(), request.getOrganizationUuid());
        final SingleErrorWithStatus<TokenAuthenticationErrorResponseModel> singleErrorWithStatus = preconditionCheckerComponent.checkPersistWithOrganization(request);
        if (singleErrorWithStatus.isPresent()) {
            return new PersistTokenAuthenticationResultResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        tokenAuthenticationService.createWithOrganization(
                new CreateTokenAuthenticationWithOrganizationDto(
                        request.getUserUuid(),
                        request.getToken(),
                        request.getExpiration(),
                        request.getOrganizationUuid()
                )
        );
        LOGGER.debug("Successfully persisted organization token of user having uuid - {} and organization uuid - {}", request.getUserUuid(), request.getOrganizationUuid());
        return new PersistTokenAuthenticationResultResponse();
    }

    @Override
    public PersistTokenAuthenticationResultResponse persistWithClientOrganization(final TokenAuthenticationPersistWithClientOrganizationRequest request) {
        LOGGER.debug("Persisting client organization token of user having uuid - {} and client uuid - {}", request.getUserUuid(), request.getClientUuid());
        final SingleErrorWithStatus<TokenAuthenticationErrorResponseModel> singleErrorWithStatus = preconditionCheckerComponent.checkPersistWithClientOrganization(request);
        if (singleErrorWithStatus.isPresent()) {
            return new PersistTokenAuthenticationResultResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        tokenAuthenticationService.createWithClientOrganization(
                new CreateTokenAuthenticationWithClientOrganizationDto(
                        request.getUserUuid(),
                        request.getToken(),
                        request.getExpiration(),
                        request.getClientUuid()
                )
        );
        LOGGER.debug("Successfully persisted client organization token of user having uuid - {} and client uuid - {}", request.getUserUuid(), request.getClientUuid());
        return new PersistTokenAuthenticationResultResponse();
    }

    @Override
    public IsExpiredTokenAuthenticationResultResponse isExpired(final String token) {
        if (StringUtils.isEmpty(token)) {
            return new IsExpiredTokenAuthenticationResultResponse(SC_UNPROCESSABLE_ENTITY, TokenAuthenticationErrorResponseModel.MISSING_TOKEN);
        }
        return tokenAuthenticationService.findByToken(token)
                .map(TokenAuthentication::isExpired)
                .map(IsExpiredTokenAuthenticationResultResponse::new)
                .orElse(new IsExpiredTokenAuthenticationResultResponse(SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.TOKEN_NOT_FOUND));
    }

    @Override
    public ExpireTokenAuthenticationByUserResultResponse expireByUser(final String userUuid) {
        LOGGER.debug("Expiring all tokens of user having uuid - {}", userUuid);
        final SingleErrorWithStatus<TokenAuthenticationErrorResponseModel> singleErrorWithStatus = checkExpireByUserForPossibleErrors(userUuid);
        if (singleErrorWithStatus.isPresent()) {
            return new ExpireTokenAuthenticationByUserResultResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        tokenAuthenticationService.expireAllByUser(userUuid);
        LOGGER.debug("Successfully expired all tokens of user having uuid - {}", userUuid);
        return new ExpireTokenAuthenticationByUserResultResponse();
    }

    @Transactional
    @Override
    public ExpireTokenAuthenticationResultResponse expire(final String token) {
        if (StringUtils.isEmpty(token)) {
            return new ExpireTokenAuthenticationResultResponse(SC_UNPROCESSABLE_ENTITY, TokenAuthenticationErrorResponseModel.MISSING_TOKEN);
        }
        final Optional<TokenAuthentication> tokenOptional = tokenAuthenticationService.findByToken(token);
        if (!tokenOptional.isPresent()) {
            return new ExpireTokenAuthenticationResultResponse(SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.TOKEN_NOT_FOUND);
        }
        tokenAuthenticationService.expire(tokenOptional.get().getUuid());
        return new ExpireTokenAuthenticationResultResponse();
    }

    @Override
    public FindTokenAuthenticationByTokenResponse findByToken(final String token) {
        if (StringUtils.isEmpty(token)) {
            return new FindTokenAuthenticationByTokenResponse(SC_UNPROCESSABLE_ENTITY, TokenAuthenticationErrorResponseModel.MISSING_TOKEN);
        }
        return tokenAuthenticationService.findByToken(token)
                .filter(authToken -> !authToken.isExpired())
                .map(authToken ->
                        new FindTokenAuthenticationByTokenResponse(
                                new FindTokenAuthenticationByTokenResponseModel(
                                        authToken.getUser().getUuid(),
                                        authToken.getUser().getEmail()
                                )
                        )
                )
                .orElseGet(() -> new FindTokenAuthenticationByTokenResponse(SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.TOKEN_NOT_FOUND));
    }

    private SingleErrorWithStatus<TokenAuthenticationErrorResponseModel> checkExpireByUserForPossibleErrors(final String userUuid) {
        if (StringUtils.isEmpty(userUuid)) {
            return SingleErrorWithStatus.of(SC_UNPROCESSABLE_ENTITY, TokenAuthenticationErrorResponseModel.MISSING_USER_UUID);
        }
        if (!userService.existsByUuid(userUuid)) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.USER_NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
    }


}
