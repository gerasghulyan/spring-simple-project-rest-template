package com.vntana.core.rest.facade.token.personalaccess.impl;

import com.vntana.core.domain.token.TokenPersonalAccess;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.CreatePersonalAccessTokenRequest;
import com.vntana.core.model.user.response.personalaccess.PersonalAccessTokenResponse;
import com.vntana.core.rest.facade.token.personalaccess.PersonalAccessTokenServiceFacade;
import com.vntana.core.service.token.personalaccess.PersonalAccessTokenService;
import com.vntana.core.service.token.personalaccess.dto.CreatePersonalAccessTokenDto;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
        LOGGER.debug("Processing personal access token facade create for request - {}", request);
        final Optional<User> foundUser = userService.findByUuid(request.getUserUuid());
        if (!foundUser.isPresent()) {
            return new PersonalAccessTokenResponse(SC_NOT_FOUND, UserErrorResponseModel.USER_NOT_FOUND);
        }
        final TokenPersonalAccess pat = personalAccessTokenService.create(new CreatePersonalAccessTokenDto(request.getUserUuid(), request.getToken()));
        return new PersonalAccessTokenResponse(pat.getToken(), pat.getUser().getUuid());
    }
}
