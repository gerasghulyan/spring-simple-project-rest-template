package com.vntana.core.service.token.invitation.user;

import com.vntana.core.domain.token.TokenUserInvitationToOrganization;
import com.vntana.core.domain.token.TokenUserInvitationToOrganizationClient;
import com.vntana.core.service.token.invitation.user.dto.CreateInvitationUserToClientDto;
import com.vntana.core.service.token.invitation.user.dto.CreateInvitationUserToOrganizationDto;

import java.util.List;
import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:14 PM
 */
public interface TokenInvitationUserService {

    TokenUserInvitationToOrganization createUserInvitationToOrganization(final CreateInvitationUserToOrganizationDto dto);
    
    List<TokenUserInvitationToOrganizationClient> createUserInvitationToClients(final CreateInvitationUserToClientDto dto);

    Optional<TokenUserInvitationToOrganization> findByToken(final String token);

    TokenUserInvitationToOrganization getByToken(final String token);

    Optional<TokenUserInvitationToOrganization> findByInvitationUserUuid(final String invitationUserUuid);

    boolean isExpired(final String token);

    boolean isExists(final String token);
}
