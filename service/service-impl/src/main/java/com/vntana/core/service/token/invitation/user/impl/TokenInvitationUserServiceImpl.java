package com.vntana.core.service.token.invitation.user.impl;

import com.vntana.core.domain.invitation.user.InvitationUser;
import com.vntana.core.domain.token.TokenInvitationUser;
import com.vntana.core.persistence.token.TokenInvitationUserRepository;
import com.vntana.core.service.invitation.user.InvitationUserService;
import com.vntana.core.service.token.invitation.user.TokenInvitationUserService;
import com.vntana.core.service.token.invitation.user.dto.CreateTokenInvitationUserDto;
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
        final InvitationUser invitationUser = invitationUserService.getByUuid(dto.getInvitationUserUuid());
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

}
