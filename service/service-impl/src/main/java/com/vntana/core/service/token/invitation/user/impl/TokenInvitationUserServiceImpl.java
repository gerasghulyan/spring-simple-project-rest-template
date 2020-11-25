package com.vntana.core.service.token.invitation.user.impl;

import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser;
import com.vntana.core.domain.invitation.user.InvitationOrganizationUser;
import com.vntana.core.domain.token.TokenUserInvitationToOrganization;
import com.vntana.core.domain.token.TokenUserInvitationToOrganizationClient;
import com.vntana.core.persistence.token.TokenUserInvitationToOrganizationClientRepository;
import com.vntana.core.persistence.token.TokenUserInvitationToOrganizationRepository;
import com.vntana.core.service.invitation.user.InvitationUserToClientService;
import com.vntana.core.service.invitation.user.InvitationUserToOrganizationService;
import com.vntana.core.service.token.invitation.user.TokenInvitationUserService;
import com.vntana.core.service.token.invitation.user.dto.CreateInvitationUserToClientDto;
import com.vntana.core.service.token.invitation.user.dto.CreateInvitationUserToOrganizationDto;
import com.vntana.core.service.token.invitation.user.exception.TokenInvitationUserNotFoundForTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:18 PM
 */
@Service
public class TokenInvitationUserServiceImpl implements TokenInvitationUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenInvitationUserServiceImpl.class);

    private final TokenUserInvitationToOrganizationRepository tokenUserInvitationToOrganizationRepository;
    private final TokenUserInvitationToOrganizationClientRepository tokenUserInvitationToClientRepository;
    private final InvitationUserToOrganizationService invitationUserToOrganizationService;
    private final InvitationUserToClientService invitationUserToClientService;

    public TokenInvitationUserServiceImpl(
            final TokenUserInvitationToOrganizationRepository tokenUserInvitationToOrganizationRepository,
            final TokenUserInvitationToOrganizationClientRepository tokenUserInvitationToClientRepository,
            final InvitationUserToOrganizationService invitationUserToOrganizationService,
            final InvitationUserToClientService invitationUserToClientService) {
        this.tokenUserInvitationToOrganizationRepository = tokenUserInvitationToOrganizationRepository;
        this.tokenUserInvitationToClientRepository = tokenUserInvitationToClientRepository;
        this.invitationUserToOrganizationService = invitationUserToOrganizationService;
        this.invitationUserToClientService = invitationUserToClientService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Transactional
    @Override
    public TokenUserInvitationToOrganization createUserInvitationToOrganization(final CreateInvitationUserToOrganizationDto dto) {
        LOGGER.debug("Creating user invitation for organization token for dto - {}", dto);
        Assert.notNull(dto, "The CreateInvitationUserToOrganizationDto should not be null");
        final InvitationOrganizationUser invitationUser = invitationUserToOrganizationService.getByUuid(dto.getInvitationUserUuid());
        final TokenUserInvitationToOrganization token = new TokenUserInvitationToOrganization(dto.getToken(), invitationUser);
        final TokenUserInvitationToOrganization savedToken = tokenUserInvitationToOrganizationRepository.save(token);
        LOGGER.debug("Successfully created user invitation for organization token for dto - {}", dto);
        return savedToken;
    }

    @Override
    public List<TokenUserInvitationToOrganizationClient> createUserInvitationToClients(final CreateInvitationUserToClientDto dto) {
        LOGGER.debug("Creating user invitation for client token for dto - {}", dto);
        Assert.notNull(dto, "The CreateInvitationUserToClientDto cannot e null");
        final List<TokenUserInvitationToOrganizationClient> tokens = dto.getTokens().stream().map(it -> {
            final InvitationOrganizationClientUser userInvitation = invitationUserToClientService.getByUuid(it.getInvitationUuid());
            return new TokenUserInvitationToOrganizationClient(it.getToken(), userInvitation);
        })
                .collect(Collectors.toList());
        final List<TokenUserInvitationToOrganizationClient> savedTokens = tokenUserInvitationToClientRepository.saveAll(tokens);
        LOGGER.debug("Successfully created user invitation for client token for dto - {}", dto);
        return savedTokens;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<TokenUserInvitationToOrganization> findByOrganizationInvitationToken(final String token) {
        LOGGER.debug("Retrieving TokenUserInvitationToOrganization by token");
        assertToken(token);
        final Optional<TokenUserInvitationToOrganization> result = tokenUserInvitationToOrganizationRepository.findByToken(token);
        LOGGER.debug("Successfully processed user invitation to organization token findByOrganizationInvitationToken method");
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<TokenUserInvitationToOrganizationClient> findByClientInvitationToken(final String token) {
        LOGGER.debug("Retrieving TokenUserInvitationToOrganizationClient by token");
        assertToken(token);
        final Optional<TokenUserInvitationToOrganizationClient> result = tokenUserInvitationToClientRepository.findByToken(token);
        LOGGER.debug("Successfully processed user invitation to client token findByClientInvitationToken method");
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public TokenUserInvitationToOrganization getByOrganizationInvitationToken(final String token) {
        assertToken(token);
        return findByOrganizationInvitationToken(token).orElseThrow(() -> new TokenInvitationUserNotFoundForTokenException(token));
    }
    
    @Transactional(readOnly = true)
    @Override
    public TokenUserInvitationToOrganizationClient getByClientInvitationToken(final String token) {
        assertToken(token);
        return findByClientInvitationToken(token).orElseThrow(() -> new TokenInvitationUserNotFoundForTokenException(token));
    }

    @Override
    public Optional<TokenUserInvitationToOrganization> findByInvitationUserUuid(final String invitationUserUuid) {
        Assert.hasText(invitationUserUuid, "The invitationUserUuid should not be null or empty");
        LOGGER.debug("Retrieving TokenInvitationUser by invitation user uuid - {}", invitationUserUuid);
        final Optional<TokenUserInvitationToOrganization> tokenInvitationUserOptional = tokenUserInvitationToOrganizationRepository.findByInvitationUserUuid(invitationUserUuid);
        LOGGER.debug("Successfully retrieved TokenInvitationUser by invitation user uuid - {}", invitationUserUuid);
        return tokenInvitationUserOptional;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isExpired(final String token) {
        assertToken(token);
        LOGGER.debug("Checking the expiration of user invitation token");
        final Optional<TokenUserInvitationToOrganization> tokenOptional = findByOrganizationInvitationToken(token);
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
        assertToken(token);
        LOGGER.debug("Checking the existence of user invitation token");
        final boolean exists = findByOrganizationInvitationToken(token).isPresent();
        LOGGER.debug("Successfully checked the existence of user invitation token");
        return exists;
    }

    private void assertToken(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
    }
}
