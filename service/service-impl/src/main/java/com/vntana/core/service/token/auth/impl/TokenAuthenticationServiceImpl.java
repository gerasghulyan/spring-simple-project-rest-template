package com.vntana.core.service.token.auth.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.token.TokenAuthentication;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.token.auth.TokenAuthenticationRepository;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.token.auth.TokenAuthenticationService;
import com.vntana.core.service.token.auth.dto.CreateTokenAuthenticationDto;
import com.vntana.core.service.token.auth.dto.CreateTokenAuthenticationWithClientOrganizationDto;
import com.vntana.core.service.token.auth.dto.CreateTokenAuthenticationWithOrganizationDto;
import com.vntana.core.service.token.auth.exception.TokenAuthenticationNotFoundForUuidException;
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
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationServiceImpl.class);

    private final UserService userService;
    private final TokenAuthenticationRepository tokenAuthenticationRepository;
    private final OrganizationService organizationService;
    private final ClientOrganizationService clientOrganizationService;

    public TokenAuthenticationServiceImpl(
            final UserService userService,
            final TokenAuthenticationRepository tokenAuthenticationRepository,
            final OrganizationService organizationService,
            final ClientOrganizationService clientOrganizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.clientOrganizationService = clientOrganizationService;
        this.userService = userService;
        this.tokenAuthenticationRepository = tokenAuthenticationRepository;
        this.organizationService = organizationService;
    }

    @Transactional
    @Override
    public TokenAuthentication create(final CreateTokenAuthenticationDto dto) {
        Assert.notNull(dto, "The CreateTokenAuthenticationDto should not be null");
        LOGGER.debug("Creating auth token for user - {}", dto.getUserUuid());
        final User user = userService.getByUuid(dto.getUserUuid());
        final TokenAuthentication tokenAuthentication = new TokenAuthentication(dto.getToken(), dto.getExpiration(), user);
        final TokenAuthentication savedTokenAuthentication = tokenAuthenticationRepository.save(tokenAuthentication);
        LOGGER.debug("Successfully creating auth token for user - {}", dto.getUserUuid());
        return savedTokenAuthentication;
    }

    @Transactional
    @Override
    public TokenAuthentication createWithOrganization(final CreateTokenAuthenticationWithOrganizationDto dto) {
        Assert.notNull(dto, "The CreateTokenAuthenticationWithOrganizationDto should not be null");
        LOGGER.debug("Creating auth token for user - {} having organization - {}", dto.getUserUuid(), dto.getOrganizationUuid());
        final User user = userService.getByUuid(dto.getUserUuid());
        final Organization organization = organizationService.getByUuid(dto.getOrganizationUuid());
        final TokenAuthentication tokenAuthentication = new TokenAuthentication(dto.getToken(), dto.getExpiration(), user, organization);
        final TokenAuthentication savedTokenAuthentication = tokenAuthenticationRepository.save(tokenAuthentication);
        LOGGER.debug("Successfully creating auth token for user - {} having organization - {}", dto.getUserUuid(), dto.getOrganizationUuid());
        return savedTokenAuthentication;
    }

    @Transactional
    @Override
    public TokenAuthentication createWithClientOrganization(final CreateTokenAuthenticationWithClientOrganizationDto dto) {
        Assert.notNull(dto, "The CreateTokenAuthenticationWithClientOrganizationDto should not be null");
        LOGGER.debug("Creating auth token for user - {} having client organization - {}", dto.getUserUuid(), dto.getClientUuid());
        final User user = userService.getByUuid(dto.getUserUuid());
        final ClientOrganization clientOrganization = clientOrganizationService.getByUuid(dto.getClientUuid());
        final TokenAuthentication tokenAuthentication = new TokenAuthentication(dto.getToken(), dto.getExpiration(), user, clientOrganization);
        final TokenAuthentication savedTokenAuthentication = tokenAuthenticationRepository.save(tokenAuthentication);
        LOGGER.debug("Successfully creating auth token for user - {} having client organization - {}", dto.getUserUuid(), dto.getClientUuid());
        return savedTokenAuthentication;
    }

    @Transactional(readOnly = true)
    @Override
    public List<TokenAuthentication> findActiveTokensByUser(final String userUuid) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        LOGGER.debug("Retrieving auth tokens by userUuid - {}", userUuid);
        final List<TokenAuthentication> tokenAuthentications = tokenAuthenticationRepository.findByUserUuidAndExpirationIsAfter(userUuid, LocalDateTime.now());
        LOGGER.debug("Retrieving auth tokens by userUuid - {}", userUuid);
        return tokenAuthentications;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<TokenAuthentication> findByUuid(final String uuid) {
        Assert.hasText(uuid, "The authentication token uuid should not be null or empty");
        LOGGER.debug("Retrieving auth token having uuid - {}", uuid);
        final Optional<TokenAuthentication> authToken = tokenAuthenticationRepository.findByUuid(uuid);
        LOGGER.debug("Successfully retrieved auth token having uuid - {}", uuid);
        return authToken;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<TokenAuthentication> findByToken(final String token) {
        Assert.hasText(token, "The authentication token uuid should not be null or empty");
        return tokenAuthenticationRepository.findByToken(token);
    }

    @Transactional(readOnly = true)
    @Override
    public TokenAuthentication getByUuid(final String uuid) {
        Assert.hasText(uuid, "The authentication token uuid should not be null or empty");
        LOGGER.debug("Retrieving auth token having uuid - {}", uuid);
        final TokenAuthentication tokenAuthentication = findByUuid(uuid).orElseThrow(() -> new TokenAuthenticationNotFoundForUuidException(uuid, TokenAuthentication.class));
        LOGGER.debug("Successfully retrieved auth token having uuid - {}", uuid);
        return tokenAuthentication;
    }

    @Transactional
    @Override
    public TokenAuthentication expire(final String tokenUuid) {
        Assert.hasText(tokenUuid, "The token uuid should not be null or empty");
        LOGGER.debug("Expiring token having uuid - {}", tokenUuid);
        final TokenAuthentication tokenAuthentication = getByUuid(tokenUuid);
        tokenAuthentication.expire();
        LOGGER.debug("Expiring token having uuid - {}", tokenUuid);
        return tokenAuthentication;
    }

    @Transactional
    @Override
    public void expireAllByUser(final String userUuid) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        LOGGER.debug("Expiring all auth tokens having user uuid - {}", userUuid);
        tokenAuthenticationRepository.updateActiveTokensExpirationByUser(userUuid, LocalDateTime.now());
        LOGGER.debug("Successfully expired all auth tokens having user uuid - {}", userUuid);
    }
}
