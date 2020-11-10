package com.vntana.core.service.token.invitation.user.impl;

import com.vntana.core.domain.invitation.user.InvitationOrganizationUser;
import com.vntana.core.domain.token.TokenInvitationUser;
import com.vntana.core.persistence.token.TokenInvitationUserRepository;
import com.vntana.core.service.invitation.user.InvitationUserService;
import com.vntana.core.service.token.invitation.user.TokenInvitationUserService;
import com.vntana.core.service.token.invitation.user.dto.CreateTokenInvitationUserDto;
import com.vntana.core.service.token.invitation.user.exception.TokenInvitationUserNotFoundForTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:18 PM
 */
@Service
public class TokenInvitationUserServiceImpl implements TokenInvitationUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenInvitationUserServiceImpl.class);

    private final TokenInvitationUserRepository tokenRepository;
    private final InvitationUserService invitationUserService;

    public TokenInvitationUserServiceImpl(final TokenInvitationUserRepository tokenRepository, final InvitationUserService invitationUserService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.tokenRepository = tokenRepository;
        this.invitationUserService = invitationUserService;
    }

    @Transactional
    @Override
    public TokenInvitationUser create(final CreateTokenInvitationUserDto dto) {
        LOGGER.debug("Creating user invitation token for dto - {}", dto);
        Assert.notNull(dto, "The CreateTokenInvitationUserDto should not be null");
        final InvitationOrganizationUser invitationUser = invitationUserService.getByUuid(dto.getInvitationUserUuid());
        final TokenInvitationUser token = new TokenInvitationUser(dto.getToken(), invitationUser);
        final TokenInvitationUser savedToken = tokenRepository.save(token);
        LOGGER.debug("Successfully created user invitation token for dto - {}", dto);
        return savedToken;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<TokenInvitationUser> findByToken(final String token) {
        LOGGER.debug("Retrieving TokenInvitationUser by token");
        Assert.hasText(token, "The token should not be null or empty");
        final Optional<TokenInvitationUser> result = tokenRepository.findByToken(token);
        LOGGER.debug("Successfully processed user invitation token findByToken method");
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public TokenInvitationUser getByToken(final String token) {
        return findByToken(token).orElseThrow(() -> new TokenInvitationUserNotFoundForTokenException(token));
    }

    @Override
    public Optional<TokenInvitationUser> findByInvitationUserUuid(final String invitationUserUuid) {
        Assert.hasText(invitationUserUuid, "The invitationUserUuid should not be null or empty");
        LOGGER.debug("Retrieving TokenInvitationUser by invitation user uuid - {}", invitationUserUuid);
        final Optional<TokenInvitationUser> tokenInvitationUserOptional = tokenRepository.findByInvitationUserUuid(invitationUserUuid);
        LOGGER.debug("Successfully retrieved TokenInvitationUser by invitation user uuid - {}", invitationUserUuid);
        return tokenInvitationUserOptional;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isExpired(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
        LOGGER.debug("Checking the expiration of user invitation token");
        final Optional<TokenInvitationUser> tokenOptional = findByToken(token);
        if (!tokenOptional.isPresent()) {
            LOGGER.error("Checking the expiration of user invitation token has been done with error, token does not exist");
            throw new TokenInvitationUserNotFoundForTokenException(token);
        }
        final boolean expired = tokenOptional.get().isExpired();
        LOGGER.debug("Successfully checked the expiration of user invitation token");
        return expired;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isExists(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
        LOGGER.debug("Checking the existence of user invitation token");
        final boolean exists = findByToken(token).isPresent();
        LOGGER.debug("Successfully checked the existence of user invitation token");
        return exists;
    }
}
