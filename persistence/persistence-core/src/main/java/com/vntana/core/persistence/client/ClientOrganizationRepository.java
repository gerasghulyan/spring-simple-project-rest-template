package com.vntana.core.persistence.client;

import com.vntana.core.domain.client.ClientOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:13 PM
 */
@Repository
public interface ClientOrganizationRepository extends JpaRepository<ClientOrganization, Long> {
    Optional<ClientOrganization> findBySlugAndOrganizationUuid(final String slug, final String organizationUuid);

    Optional<ClientOrganization> findByUuid(final String uuid);

    boolean existsByUuid(String uuid);
}
