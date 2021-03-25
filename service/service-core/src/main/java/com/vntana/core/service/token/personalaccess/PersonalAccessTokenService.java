package com.vntana.core.service.token.personalaccess;

import com.vntana.core.domain.token.TokenPersonalAccess;
import com.vntana.core.service.token.personalaccess.dto.CreatePersonalAccessTokenDto;
import com.vntana.core.service.token.personalaccess.dto.RegeneratePersonalAccessTokenDto;

import java.util.Optional;

/**
 * Created by Diana Gevorgyan
 * Date: 3/23/21
 * Time: 10:37 AM
 */
public interface PersonalAccessTokenService {

    TokenPersonalAccess create(CreatePersonalAccessTokenDto dto);

    Optional<TokenPersonalAccess> findByToken(final String token);

    Optional<TokenPersonalAccess> findByUser(final String userUuid);

    TokenPersonalAccess expire(final String tokenUuid);

    TokenPersonalAccess regenerateTokenForUser(final RegeneratePersonalAccessTokenDto dto);
}
