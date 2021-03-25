package com.vntana.core.rest.facade.token.personalaccess.impl;

import com.vntana.core.domain.token.TokenPersonalAccess;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.token.auth.request.TokenAuthenticationRequest;
import com.vntana.core.model.token.auth.response.model.FindTokenAuthenticationByTokenResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.CreatePersonalAccessTokenRequest;
import com.vntana.core.model.user.request.RegeneratePersonalAccessKeyTokenRequest;
import com.vntana.core.model.user.response.personalaccess.FindByPersonalAccessTokenResponseModel;
import com.vntana.core.model.user.response.personalaccess.PersonalAccessTokenResponse;
import com.vntana.core.rest.facade.token.personalaccess.PersonalAccessTokenServiceFacade;
import com.vntana.core.service.token.personalaccess.PersonalAccessTokenService;
import com.vntana.core.service.token.personalaccess.dto.CreatePersonalAccessTokenDto;
import com.vntana.core.service.token.personalaccess.dto.RegeneratePersonalAccessTokenDto;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 1:41 AM
 */
@Component
public class PersonalAccessTokenServiceFacadeImpl implements PersonalAccessTokenServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonalAccessTokenServiceFacadeImpl.class);

    private final PersonalAccessTokenService personalAccessTokenService;
    private final UserService userService;

    public PersonalAccessTokenServiceFacadeImpl(final PersonalAccessTokenService personalAccessTokenService, final UserService userService) {
        this.personalAccessTokenService = personalAccessTokenService;
        this.userService = userService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public PersonalAccessTokenResponse create(final CreatePersonalAccessTokenRequest request) {
        LOGGER.debug("Processing personal access token facade create for user uuid - {}", request.getUserUuid());
        final Optional<User> foundUser = userService.findByUuid(request.getUserUuid());
        if (!foundUser.isPresent()) {
            LOGGER.debug("Cannot processes personal access token facade create for user uuid - {}, cannot find user", request.getUserUuid());
            return new PersonalAccessTokenResponse(SC_NOT_FOUND, UserErrorResponseModel.USER_NOT_FOUND);
        }
        final TokenPersonalAccess pat = personalAccessTokenService.create(new CreatePersonalAccessTokenDto(request.getUserUuid(), request.getToken()));
        LOGGER.debug("Successfully processed personal access token facade create for user uuid - {} ", request.getUserUuid());
        return new PersonalAccessTokenResponse(pat.getToken(), pat.getUser().getUuid());
    }

    @Override
    public PersonalAccessTokenResponse expireByUserUuid(final String userUuid) {
        LOGGER.debug("Processing personal access token facade expireByUserUuid for user uuid - {}", userUuid);
        final Optional<User> foundUser = userService.findByUuid(userUuid);
        if (!foundUser.isPresent()) {
            LOGGER.debug("Cannot processes personal access token facade expireByUserUuid for user uuid - {}, cannot find user", userUuid);
            return new PersonalAccessTokenResponse(SC_NOT_FOUND, UserErrorResponseModel.USER_NOT_FOUND);
        }
        final Optional<TokenPersonalAccess> foundToken = personalAccessTokenService.findByUser(userUuid);
        foundToken.ifPresent(tokenPersonalAccess -> personalAccessTokenService.expire(tokenPersonalAccess.getUuid()));
        LOGGER.debug("Successfully processed personal access token facade expireByUserUuid for user uuid - {}", userUuid);
        return new PersonalAccessTokenResponse(null, userUuid);
    }

    @Override
    public FindByPersonalAccessTokenResponseModel findByToken(final TokenAuthenticationRequest request) {
        LOGGER.debug("Processing personal access token facade findByToken method");
        final Optional<TokenPersonalAccess> foundToken = personalAccessTokenService.findByToken(request.getToken());
        if (foundToken.isPresent()) {
            LOGGER.debug("Successfully processed personal access token facade findByToken method");
            return new FindByPersonalAccessTokenResponseModel(
                    new FindTokenAuthenticationByTokenResponseModel(foundToken.get().getUser().getUuid(), foundToken.get().getUser().getEmail()));
        }
        LOGGER.debug("Cannot process personal access token facade findByToken method");
        return new FindByPersonalAccessTokenResponseModel(SC_FORBIDDEN, UserErrorResponseModel.TOKEN_NOT_FOUND);
    }

    @Override
    public PersonalAccessTokenResponse findByUserUuid(final String userUuid) {
        LOGGER.debug("Processing personal access token facade findByUserUuid for user uuid - {}", userUuid);
        final Optional<User> foundUser = userService.findByUuid(userUuid);
        if (!foundUser.isPresent()) {
            LOGGER.debug("Cannot processes personal access token facade create for user uuid - {}, cannot find user", userUuid);
            return new PersonalAccessTokenResponse(SC_NOT_FOUND, UserErrorResponseModel.USER_NOT_FOUND);
        }
        final Optional<TokenPersonalAccess> foundToken = personalAccessTokenService.findByUser(userUuid);
        if (foundToken.isPresent()) {
            LOGGER.debug("Successfully processed personal access token facade findByUserUuid for user uuid - {} ", userUuid);
            return new PersonalAccessTokenResponse(foundToken.get().getToken(), foundToken.get().getUser().getUuid());
        }
        LOGGER.debug("Cannot process personal access token facade findByUserUuid for user uuid - {} ", userUuid);
        return new PersonalAccessTokenResponse(SC_NOT_FOUND, UserErrorResponseModel.TOKEN_NOT_FOUND);
    }

    @Override
    public PersonalAccessTokenResponse regenerate(final RegeneratePersonalAccessKeyTokenRequest request) {
        LOGGER.debug("Processing personal access token facade regenerate for user uuid - {}", request.getUserUuid());
        final Optional<User> foundUser = userService.findByUuid(request.getUserUuid());
        if (!foundUser.isPresent()) {
            LOGGER.debug("Cannot processes personal access token facade regenerate for user uuid - {}, cannot find user", request.getUserUuid());
            return new PersonalAccessTokenResponse(SC_NOT_FOUND, UserErrorResponseModel.USER_NOT_FOUND);
        }
        final TokenPersonalAccess pat = personalAccessTokenService.regenerateTokenForUser(new RegeneratePersonalAccessTokenDto(request.getUserUuid(), request.getToken()));
        LOGGER.debug("Successfully processed personal access token facade regenerate for user uuid - {} ", request.getUserUuid());
        return new PersonalAccessTokenResponse(pat.getToken(), pat.getUser().getUuid());
    }
}
