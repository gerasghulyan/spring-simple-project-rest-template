package com.vntana.core.service.token;

import com.vntana.core.domain.token.AbstractToken;

import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 12:44 PM
 */
public interface TokenService {

    Optional<AbstractToken> findByToken(final String token);

    AbstractToken getByToken(final String token);

    Optional<AbstractToken> findByUuid(final String uuid);

    AbstractToken getByUuid(final String uuid);

    AbstractToken findByTokenAndExpire(final String token);

    AbstractToken expire(final String tokenUuid);

    boolean existsByToken(final String token);
}
