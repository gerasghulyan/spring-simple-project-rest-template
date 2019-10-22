package com.vntana.core.service.token.impl;

import com.vntana.core.domain.token.AbstractToken;
import com.vntana.core.persistence.token.TokenRepository;
import com.vntana.core.service.token.TokenService;
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

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(final TokenRepository tokenRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.tokenRepository = tokenRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AbstractToken> findByToken(final String token) {
        Assert.notNull(token, "The token should not be null");
        LOGGER.debug("Trying to find token entity for token - {}", token);
        return tokenRepository.findByToken(token);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AbstractToken> findByUuid(final String uuid) {
        Assert.notNull(uuid, "The uuid should not be null");
        return tokenRepository.findByUuid(uuid);
    }
}
