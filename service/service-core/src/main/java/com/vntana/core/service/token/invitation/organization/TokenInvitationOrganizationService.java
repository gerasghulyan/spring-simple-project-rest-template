package com.vntana.core.service.token.invitation.organization;

import com.vntana.core.domain.token.TokenInvitationOrganization;
import com.vntana.core.service.token.invitation.organization.dto.CreateTokenInvitationOrganizationDto;

import java.util.Optional;

/**
 * Created by Geras Ghulyan
 * Date: 10.04.20
 * Time: 19:20
 */
public interface TokenInvitationOrganizationService {

    TokenInvitationOrganization create(final CreateTokenInvitationOrganizationDto dto);

    Optional<TokenInvitationOrganization> findByToken(final String token);

    boolean existsByToken(final String token);
    
    TokenInvitationOrganization getByToken(final String token);
}
