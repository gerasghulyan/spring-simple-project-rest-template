package com.vntana.core.persistence.token;

import com.vntana.core.domain.token.TokenInvitationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:19 PM
 */
public interface TokenInvitationUserRepository extends JpaRepository<TokenInvitationUser, Long> {

    Optional<TokenInvitationUser> findByToken(final String token);
}
