package com.vntana.core.persistence.token;

import com.vntana.core.domain.token.AbstractToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 12:40 PM
 */
@Repository
public interface TokenRepository extends JpaRepository<AbstractToken, Long> {
    Optional<AbstractToken> findByToken(final String token);
}
