package com.vntana.core.persistence.token.auth;

import com.vntana.core.domain.token.TokenAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 2:17 PM
 */
@Repository
public interface TokenAuthenticationRepository extends JpaRepository<TokenAuthentication, Long> {

    Optional<TokenAuthentication> findByUuid(final String uuid);

    Optional<TokenAuthentication> findByToken(final String token);

    List<TokenAuthentication> findByUserUuidAndExpirationIsAfter(final String userUuid, final LocalDateTime now);

    @Modifying
    @Query("update TokenAuthentication a set a.expiration=:expiration where a.user.id in (select u.id from User u where u.uuid=:userUuid)")
    void updateActiveTokensExpirationByUser(@Param("userUuid") final String userUuid, @Param("expiration") final LocalDateTime expiration);
}
