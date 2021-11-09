package com.project.sample.persistence.organization;

import com.project.sample.commons.persistence.domain.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Geras Ghulyan.
 * Date: 10/8/19
 * Time: 5:13 PM
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByUuid(final String uuid);

    boolean existsByUuid(final String uuid);
}
