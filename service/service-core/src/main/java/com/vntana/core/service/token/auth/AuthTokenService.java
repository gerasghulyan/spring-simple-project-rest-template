package com.vntana.core.service.token.auth;

import com.vntana.core.domain.token.AuthToken;
import com.vntana.core.service.token.auth.dto.AuthTokenCreateDto;

import java.util.List;
import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 2:12 PM
 */
public interface AuthTokenService {

    AuthToken create(final AuthTokenCreateDto dto);

    List<AuthToken> findActiveTokensByUser(final String userUuid);

    Optional<AuthToken> findByUuid(final String uuid);

    Optional<AuthToken> findByToken(final String token);

    AuthToken getByUuid(final String uuid);

    AuthToken expire(final String tokenUuid);

    void expireAllByUser(final String userUuid);
}
