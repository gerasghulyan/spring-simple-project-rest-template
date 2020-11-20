package com.vntana.core.persistence.token;

import com.vntana.core.domain.token.TokenUserInvitationToOrganizationClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 2:57 PM
 */
@Repository
public interface TokenUserInvitationToOrganizationClientRepository extends JpaRepository<TokenUserInvitationToOrganizationClient, Long> {

    Optional<TokenUserInvitationToOrganizationClient> findByToken(final String token);

    Optional<TokenUserInvitationToOrganizationClient> findByUserInvitationUuid(final String userInvitationUuid);
}
