package com.vntana.core.persistence.token.personalaccess;

import com.vntana.core.domain.token.TokenPersonalAccess;
import com.vntana.core.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Diana Gevorgyan
 * Date: 3/22/21
 * Time: 5:55 PM
 */
@Repository
public interface TokenPersonalAccessRepository extends CrudRepository<TokenPersonalAccess, Long> {

    Optional<TokenPersonalAccess> findTokenPersonalAccessByUserAndExpirationIsNull(final User user);

    Optional<TokenPersonalAccess> findByUuid(final String tokenUuid);

    Optional<TokenPersonalAccess> findByToken(final String token);

    List<TokenPersonalAccess> findAllByUserAndExpirationIsNull(final User user);
}
