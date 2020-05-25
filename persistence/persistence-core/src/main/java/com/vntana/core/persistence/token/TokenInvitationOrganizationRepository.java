package com.vntana.core.persistence.token;

import com.vntana.core.domain.token.TokenInvitationOrganization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Geras Ghulyan
 * Date: 11.04.20
 * Time: 21:00
 */
@Repository
public interface TokenInvitationOrganizationRepository extends CrudRepository<TokenInvitationOrganization, Long> {
    Optional<TokenInvitationOrganization> findByToken(final String token);

    boolean existsByToken(final String token);
}
