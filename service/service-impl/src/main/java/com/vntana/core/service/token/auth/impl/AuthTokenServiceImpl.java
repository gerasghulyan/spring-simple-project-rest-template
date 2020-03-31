package com.vntana.core.service.token.auth.impl;

import com.vntana.core.domain.token.AuthToken;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.token.auth.AuthTokenRepository;
import com.vntana.core.service.token.auth.AuthTokenService;
import com.vntana.core.service.token.auth.dto.AuthTokenCreateDto;
import com.vntana.core.service.token.auth.exception.AuthTokenNotFoundForUuidException;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 2:16 PM
 */
@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenServiceImpl.class);

    private final UserService userService;
    private final AuthTokenRepository authTokenRepository;

    public AuthTokenServiceImpl(final UserService userService, final AuthTokenRepository authTokenRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.authTokenRepository = authTokenRepository;
    }

    @Transactional
    @Override
    public AuthToken create(final AuthTokenCreateDto dto) {
        Assert.notNull(dto, "The AuthTokenCreateDto should not be null");
        LOGGER.debug("Creating auth token for user - {}", dto.getUserUuid());
        final User user = userService.getByUuid(dto.getUserUuid());
        final AuthToken authToken = new AuthToken(dto.getToken(), user);
        final AuthToken savedAuthToken = authTokenRepository.save(authToken);
        LOGGER.debug("Successfully creating auth token for user - {}", dto.getUserUuid());
        return savedAuthToken;
    }

    @Override
    public List<AuthToken> findActiveTokensByUser(final String userUuid) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        LOGGER.debug("Retrieving auth tokens by userUuid - {}", userUuid);
        final List<AuthToken> authTokens = authTokenRepository.findByUserUuidAndExpirationIsNull(userUuid);
        LOGGER.debug("Retrieving auth tokens by userUuid - {}", userUuid);
        return authTokens;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AuthToken> findByUuid(final String uuid) {
        Assert.hasText(uuid, "The AuthToken uuid should not be null or empty");
        LOGGER.debug("Retrieving auth token having uuid - {}", uuid);
        final Optional<AuthToken> authToken = authTokenRepository.findByUuid(uuid);
        LOGGER.debug("Successfully retrieved auth token having uuid - {}", uuid);
        return authToken;
    }

    @Override
    public Optional<AuthToken> findByToken(final String token) {
        Assert.hasText(token, "The AuthToken uuid should not be null or empty");
        return authTokenRepository.findByToken(token);
    }

    @Override
    public AuthToken getByUuid(final String uuid) {
        Assert.hasText(uuid, "The AuthToken uuid should not be null or empty");
        LOGGER.debug("Retrieving auth token having uuid - {}", uuid);
        final AuthToken authToken = findByUuid(uuid).orElseThrow(() -> new AuthTokenNotFoundForUuidException(uuid, AuthToken.class));
        LOGGER.debug("Successfully retrieved auth token having uuid - {}", uuid);
        return authToken;
    }

    @Transactional
    @Override
    public AuthToken expire(final String tokenUuid) {
        Assert.hasText(tokenUuid, "The token uuid should not be null or empty");
        LOGGER.debug("Expiring token having uuid - {}", tokenUuid);
        final AuthToken authToken = getByUuid(tokenUuid);
        authToken.expire();
        LOGGER.debug("Expiring token having uuid - {}", tokenUuid);
        return authToken;
    }

    @Transactional
    @Override
    public void expireAllByUser(final String userUuid) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        LOGGER.debug("Expiring all auth tokens having user uuid - {}", userUuid);
        authTokenRepository.updateActiveTokensExpirationByUser(userUuid, LocalDateTime.now());
        LOGGER.debug("Successfully expired all auth tokens having user uuid - {}", userUuid);
    }
}
