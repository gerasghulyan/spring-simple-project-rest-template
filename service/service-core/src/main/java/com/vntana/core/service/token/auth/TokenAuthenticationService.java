package com.vntana.core.service.token.auth;

import com.vntana.core.domain.token.TokenAuthentication;
import com.vntana.core.service.token.auth.dto.CreateTokenAuthenticationDto;
import com.vntana.core.service.token.auth.dto.CreateTokenAuthenticationWithClientOrganizationDto;
import com.vntana.core.service.token.auth.dto.CreateTokenAuthenticationWithOrganizationDto;

import java.util.List;
import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 2:12 PM
 */
public interface TokenAuthenticationService {

    TokenAuthentication create(final CreateTokenAuthenticationDto dto);

    TokenAuthentication createWithOrganization(final CreateTokenAuthenticationWithOrganizationDto dto);

    TokenAuthentication createWithClientOrganization(final CreateTokenAuthenticationWithClientOrganizationDto dto);

    List<TokenAuthentication> findActiveTokensByUser(final String userUuid);

    Optional<TokenAuthentication> findByUuid(final String uuid);

    Optional<TokenAuthentication> findByToken(final String token);

    TokenAuthentication getByUuid(final String uuid);

    TokenAuthentication expire(final String tokenUuid);

    void expireAllByUser(final String userUuid);
}
