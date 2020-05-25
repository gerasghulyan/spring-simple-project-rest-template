package com.vntana.core.service.token.invitation.organization.impl;

import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.domain.token.TokenInvitationOrganization;
import com.vntana.core.persistence.token.TokenInvitationOrganizationRepository;
import com.vntana.core.persistence.token.TokenRepository;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import com.vntana.core.service.token.exception.TokenNotFoundException;
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService;
import com.vntana.core.service.token.invitation.organization.dto.CreateTokenInvitationOrganizationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created by Geras Ghulyan
 * Date: 12.04.20
 * Time: 02:00
 */
@Service
public class TokenInvitationOrganizationServiceImpl implements TokenInvitationOrganizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenInvitationOrganizationServiceImpl.class);

    private final InvitationOrganizationService invitationOrganizationService;
    private final TokenRepository tokenRepository;
    private final TokenInvitationOrganizationRepository tokenInvitationOrganizationRepository;

    public TokenInvitationOrganizationServiceImpl(
            final InvitationOrganizationService invitationOrganizationService,
            final TokenRepository tokenRepository,
            final TokenInvitationOrganizationRepository tokenInvitationOrganizationRepository) {
        this.invitationOrganizationService = invitationOrganizationService;
        this.tokenRepository = tokenRepository;
        this.tokenInvitationOrganizationRepository = tokenInvitationOrganizationRepository;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Transactional
    @Override
    public TokenInvitationOrganization create(final CreateTokenInvitationOrganizationDto dto) {
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
    public Optional<TokenInvitationOrganization> findByToken(final String token) {
        LOGGER.debug("Retrieving TokenInvitationOrganization by token");
        assertToken(token);
        return tokenInvitationOrganizationRepository.findByToken(token);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByToken(final String token) {
        LOGGER.debug("Checking existence of TokenInvitationOrganization by token");
        assertToken(token);
        return tokenInvitationOrganizationRepository.existsByToken(token);
    }
    
    @Transactional(readOnly = true)
    @Override
    public TokenInvitationOrganization getByToken(final String token) {
        return findByToken(token).orElseThrow(() -> new TokenNotFoundException(String.format("Can not find TokenInvitationOrganization for token - %s", token)));
    }

    private void assertToken(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
    }
}
