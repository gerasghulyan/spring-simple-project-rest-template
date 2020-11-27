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

    Optional<TokenUserInvitationToOrganization> findByOrganizationInvitationToken(final String token);

    Optional<TokenUserInvitationToOrganizationClient> findByClientInvitationToken(final String token);

    TokenUserInvitationToOrganization getByOrganizationInvitationToken(final String token);

    TokenUserInvitationToOrganizationClient getByClientInvitationToken(final String token);

    Optional<TokenUserInvitationToOrganization> findByInvitationUserUuid(final String invitationUserUuid);

    boolean isInvitationToOrganizationExpired(final String token);

    boolean isInvitationToClientExpired(String token);

    boolean existsInvitationToOrganizationByToken(final String token);

    boolean existsInvitationToClientByToken(final String token);
}
