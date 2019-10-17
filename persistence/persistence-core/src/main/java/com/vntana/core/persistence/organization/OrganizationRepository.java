package com.vntana.core.persistence.organization;

import com.vntana.core.domain.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:13 PM
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findBySlug(final String slug);

    Optional<Organization> findByUuid(final String uuid);
}
