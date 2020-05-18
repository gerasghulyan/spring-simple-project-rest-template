package com.vntana.core.service.token.invitation.user;

import com.vntana.core.domain.token.TokenInvitationUser;
import com.vntana.core.service.token.invitation.user.dto.CreateTokenInvitationUserDto;

import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:14 PM
 */
public interface TokenInvitationUserService {

    TokenInvitationUser create(final CreateTokenInvitationUserDto dto);

    Optional<TokenInvitationUser> findByToken(final String token);

    TokenInvitationUser getByToken(final String token);

    Optional<TokenInvitationUser> findByInvitationUserUuid(final String invitationUserUuid);

    boolean isExpired(final String token);

    boolean isExists(final String token);
}
