package com.vntana.core.persistence.client;

import com.vntana.core.domain.client.ClientOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:13 PM
 */
public interface ClientOrganizationRepository extends JpaRepository<ClientOrganization, Long> {
    Optional<ClientOrganization> findBySlug(final String slug);

    Optional<ClientOrganization> findByUuid(final String uuid);
}
