package com.vntana.core.persistence.user.anonymous;

import com.vntana.core.domain.user.anonymousUser.AnonymousUser;
import com.vntana.core.domain.user.anonymousUser.AnonymousUserSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 12:22 PM
 */
public interface AnonymousUserRepository extends JpaRepository<AnonymousUser, String> {

    Optional<AnonymousUser> findByExternalUuidAndSource(final String externalUuid, final AnonymousUserSource source);
}
