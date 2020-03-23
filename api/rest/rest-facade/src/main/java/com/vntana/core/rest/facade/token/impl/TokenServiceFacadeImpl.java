package com.vntana.core.rest.facade.token.impl;

import com.vntana.core.domain.token.AbstractToken;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import com.vntana.core.model.token.response.TokenExpireResultResponse;
import com.vntana.core.model.token.response.TokenIsExpiredResultResponse;
import com.vntana.core.rest.facade.token.TokenServiceFacade;
import com.vntana.core.service.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:50 PM
 */
@Component
public class TokenServiceFacadeImpl implements TokenServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceFacadeImpl.class);

    private final TokenService tokenService;

    public TokenServiceFacadeImpl(final TokenService tokenService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.tokenService = tokenService;
    }

    @Override
    public TokenIsExpiredResultResponse isExpired(final String token) {
        if (StringUtils.isEmpty(token)) {
            return new TokenIsExpiredResultResponse(SC_UNPROCESSABLE_ENTITY, TokenErrorResponseModel.MISSING_TOKEN);
        }
        return tokenService.findByToken(token)
                .map(AbstractToken::isExpired)
                .map(TokenIsExpiredResultResponse::new)
                .orElse(new TokenIsExpiredResultResponse(SC_NOT_FOUND, TokenErrorResponseModel.TOKEN_NOT_FOUND));
    }

    @Override
    public TokenExpireResultResponse expire(final String token) {
        if (StringUtils.isEmpty(token)) {
            return new TokenExpireResultResponse(SC_UNPROCESSABLE_ENTITY, TokenErrorResponseModel.MISSING_TOKEN);
        }
        final Optional<AbstractToken> tokenOptional = tokenService.findByToken(token);
        if (!tokenOptional.isPresent()) {
            return new TokenExpireResultResponse(SC_NOT_FOUND, TokenErrorResponseModel.TOKEN_NOT_FOUND);
        }
        tokenService.expire(tokenOptional.get().getUuid());
        return new TokenExpireResultResponse();
    }
}
