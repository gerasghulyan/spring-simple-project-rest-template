package com.vntana.core.persistence.token.userinvitation;

import com.vntana.core.domain.token.TokenUserInvitationToOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:19 PM
 */
@Repository
public interface TokenUserInvitationToOrganizationRepository extends JpaRepository<TokenUserInvitationToOrganization, Long> {

    Optional<TokenUserInvitationToOrganization> findByToken(final String token);

    Optional<TokenUserInvitationToOrganization> findByInvitationUserUuid(final String invitationUserUuid);
}
