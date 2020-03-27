package com.vntana.core.service.token;

import com.vntana.core.domain.token.AbstractToken;
import com.vntana.core.domain.token.TokenInvitationOrganization;
import com.vntana.core.service.token.dto.CreateTokenInvitationOrganizationDto;

import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 12:44 PM
 */
public interface TokenService {

    TokenInvitationOrganization createTokenInvitationOrganization(CreateTokenInvitationOrganizationDto dto);

    Optional<AbstractToken> findByToken(final String token);

    Optional<AbstractToken> findByUuid(final String uuid);

    AbstractToken getByUuid(final String uuid);

    AbstractToken expire(final String tokenUuid);

}
