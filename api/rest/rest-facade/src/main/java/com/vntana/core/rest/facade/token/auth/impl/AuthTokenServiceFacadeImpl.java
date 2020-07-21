package com.vntana.core.rest.facade.token.auth.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.token.AuthToken;
import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel;
import com.vntana.core.model.token.auth.request.AuthTokenPersistRequest;
import com.vntana.core.model.token.auth.request.AuthTokenPersistWithOrganizationRequest;
import com.vntana.core.model.token.auth.response.*;
import com.vntana.core.model.token.auth.response.model.AuthTokenFindByTokenResponseModel;
import com.vntana.core.rest.facade.token.auth.AuthTokenServiceFacade;
import com.vntana.core.service.token.auth.AuthTokenService;
import com.vntana.core.service.token.auth.dto.AuthTokenCreateDto;
import com.vntana.core.service.token.auth.dto.AuthTokenCreateWithOrganizationDto;
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
public class AuthTokenServiceFacadeImpl implements AuthTokenServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenServiceFacadeImpl.class);

    private final AuthTokenService authTokenService;
    private final UserService userService;

    public AuthTokenServiceFacadeImpl(
            final AuthTokenService authTokenService,
            final UserService userService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.authTokenService = authTokenService;
        this.userService = userService;
    }

    @Override
    public AuthTokenPersistResultResponse persist(final AuthTokenPersistRequest request) {
        LOGGER.debug("Persisting token of user having uuid - {}", request.getToken());
        if (!userService.existsByUuid(request.getUserUuid())) {
            return new AuthTokenPersistResultResponse(SC_NOT_FOUND, AuthTokenErrorResponseModel.USER_NOT_FOUND);
        }
        authTokenService.create(new AuthTokenCreateDto(request.getUserUuid(), request.getToken(), request.getExpiration()));
        LOGGER.debug("Successfully persisted token of user having uuid - {}", request.getToken());
        return new AuthTokenPersistResultResponse();
    }

    @Override
    public AuthTokenPersistResultResponse persistWithOrganization(final AuthTokenPersistWithOrganizationRequest request) {
        LOGGER.debug("Persisting token of user having uuid - {}", request.getToken());
        if (!userService.existsByUuid(request.getUserUuid())) {
            return new AuthTokenPersistResultResponse(SC_NOT_FOUND, AuthTokenErrorResponseModel.USER_NOT_FOUND);
        }
        authTokenService.createWithOrganization(
                new AuthTokenCreateWithOrganizationDto(
                        request.getUserUuid(),
                        request.getToken(),
                        request.getOrganizationUuid(),
                        request.getExpiration())
        );
        LOGGER.debug("Successfully persisted token of user having uuid - {}", request.getToken());
        return new AuthTokenPersistResultResponse();
    }

    @Override
    public AuthTokenIsExpiredResultResponse isExpired(final String token) {
        if (StringUtils.isEmpty(token)) {
            return new AuthTokenIsExpiredResultResponse(SC_UNPROCESSABLE_ENTITY, AuthTokenErrorResponseModel.MISSING_TOKEN);
        }
        return authTokenService.findByToken(token)
                .map(AuthToken::isExpired)
                .map(AuthTokenIsExpiredResultResponse::new)
                .orElse(new AuthTokenIsExpiredResultResponse(SC_NOT_FOUND, AuthTokenErrorResponseModel.TOKEN_NOT_FOUND));
    }

    @Override
    public AuthTokenExpireByUserResultResponse expireByUser(final String userUuid) {
        LOGGER.debug("Expiring all tokens of user having uuid - {}", userUuid);
        final SingleErrorWithStatus<AuthTokenErrorResponseModel> singleErrorWithStatus = checkExpireByUserForPossibleErrors(userUuid);
        if (singleErrorWithStatus.isPresent()) {
            return new AuthTokenExpireByUserResultResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        authTokenService.expireAllByUser(userUuid);
        LOGGER.debug("Successfully expired all tokens of user having uuid - {}", userUuid);
        return new AuthTokenExpireByUserResultResponse();
    }

    @Transactional
    @Override
    public AuthTokenExpireResultResponse expire(final String token) {
        if (StringUtils.isEmpty(token)) {
            return new AuthTokenExpireResultResponse(SC_UNPROCESSABLE_ENTITY, AuthTokenErrorResponseModel.MISSING_TOKEN);
        }
        final Optional<AuthToken> tokenOptional = authTokenService.findByToken(token);
        if (!tokenOptional.isPresent()) {
            return new AuthTokenExpireResultResponse(SC_NOT_FOUND, AuthTokenErrorResponseModel.TOKEN_NOT_FOUND);
        }
        authTokenService.expire(tokenOptional.get().getUuid());
        return new AuthTokenExpireResultResponse();
    }

    @Override
    public AuthTokenFindByTokenResponse findByToken(final String token) {
        if (StringUtils.isEmpty(token)) {
            return new AuthTokenFindByTokenResponse(SC_UNPROCESSABLE_ENTITY, AuthTokenErrorResponseModel.MISSING_TOKEN);
        }
        return authTokenService.findByToken(token)
                .filter(authToken -> !authToken.isExpired())
                .map(authToken ->
                        new AuthTokenFindByTokenResponse(
                                new AuthTokenFindByTokenResponseModel(
                                        authToken.getUser().getUuid(),
                                        authToken.getUser().getEmail()
                                )
                        )
                )
                .orElseGet(() -> new AuthTokenFindByTokenResponse(SC_NOT_FOUND, AuthTokenErrorResponseModel.TOKEN_NOT_FOUND));
    }

    private SingleErrorWithStatus<AuthTokenErrorResponseModel> checkExpireByUserForPossibleErrors(final String userUuid) {
        if (StringUtils.isEmpty(userUuid)) {
            return SingleErrorWithStatus.of(SC_UNPROCESSABLE_ENTITY, AuthTokenErrorResponseModel.MISSING_USER_UUID);
        }
        if (!userService.existsByUuid(userUuid)) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, AuthTokenErrorResponseModel.USER_NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
    }


}
