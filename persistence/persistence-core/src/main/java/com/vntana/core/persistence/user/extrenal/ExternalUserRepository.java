package com.vntana.core.persistence.user.extrenal;

import com.vntana.core.domain.user.external.ExternalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 12:22 PM
 */
public interface ExternalUserRepository extends JpaRepository<ExternalUser, String> {

    Optional<ExternalUser> findByExternalUuid(final String externalUuid);
}
