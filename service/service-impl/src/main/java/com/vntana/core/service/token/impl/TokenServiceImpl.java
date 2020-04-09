package com.vntana.core.service.token.impl;

import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.domain.token.AbstractToken;
import com.vntana.core.domain.token.TokenInvitationOrganization;
import com.vntana.core.persistence.token.TokenRepository;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import com.vntana.core.service.token.TokenService;
import com.vntana.core.service.token.dto.CreateTokenInvitationOrganizationDto;
import com.vntana.core.service.token.exception.TokenNotFoundException;
import com.vntana.core.service.token.exception.TokenNotFoundForUuidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 12:50 PM
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    private final InvitationOrganizationService invitationOrganizationService;
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(final InvitationOrganizationService invitationOrganizationService, final TokenRepository tokenRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.invitationOrganizationService = invitationOrganizationService;
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    @Override
    public TokenInvitationOrganization createTokenInvitationOrganization(final CreateTokenInvitationOrganizationDto dto) {
        LOGGER.debug("Creating organization invitation token for dto - {}", dto);
        Assert.notNull(dto, "The CreateInvitationOrganizationDto should not be null");
        final InvitationOrganization invitationOrganization = invitationOrganizationService.getByUuid(dto.getInvitationOrganizationUuid());
        final TokenInvitationOrganization token = new TokenInvitationOrganization(dto.getToken(), invitationOrganization);
        final TokenInvitationOrganization savedToken = tokenRepository.save(token);
        LOGGER.debug("Successfully created organization invitation token for dto - {}", dto);
        return savedToken;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AbstractToken> findByToken(final String token) {
        Assert.hasText(token, "The token should not be null");
        LOGGER.debug("Trying to find token entity for token - {}", token);
        return tokenRepository.findByToken(token);
    }

    @Transactional(readOnly = true)
    @Override
    public AbstractToken getByToken(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
        LOGGER.debug("Trying to retrieve abstract token");
        final AbstractToken abstractToken = findByToken(token).orElseThrow(() -> new TokenNotFoundException("Unable to find token"));
        LOGGER.debug("Successfully retrieved abstract token");
        return abstractToken;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AbstractToken> findByUuid(final String uuid) {
        Assert.notNull(uuid, "The uuid should not be null");
        return tokenRepository.findByUuid(uuid);
    }

    @Transactional
    @Override
    public AbstractToken getByUuid(final String uuid) {
        Assert.hasText(uuid, "The AbstractToken uuid should not be null or empty");
        LOGGER.debug("Retrieving abstract token having uuid - {}", uuid);
        final AbstractToken token = findByUuid(uuid).orElseThrow(() -> new TokenNotFoundForUuidException(uuid, AbstractToken.class));
        LOGGER.debug("Successfully retrieved abstract token having uuid - {}", uuid);
        return token;
    }

    @Transactional
    @Override
    public AbstractToken findByTokenAndExpire(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
        LOGGER.debug("Try to find and expire token");
        final AbstractToken expiredToken = getByToken(token);
        expiredToken.expire();
        LOGGER.debug("Successfully found and expired token");
        return expiredToken;
    }

    @Transactional
    @Override
    public AbstractToken expire(final String tokenUuid) {
        Assert.hasText(tokenUuid, "The token uuid should not be null or empty");
        LOGGER.debug("Expiring token having uuid - {}", tokenUuid);
        final AbstractToken token = getByUuid(tokenUuid);
        token.expire();
        LOGGER.debug("Expiring token having uuid - {}", tokenUuid);
        return token;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByToken(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
        LOGGER.debug("Checking existence of AbstractToken");
        final boolean existence = findByToken(token).isPresent();
        LOGGER.debug("Successfully checked existence of AbstractToken");
        return existence;
    }
}
