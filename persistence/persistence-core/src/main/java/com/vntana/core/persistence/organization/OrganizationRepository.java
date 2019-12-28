package com.vntana.core.persistence.organization;

import com.vntana.core.domain.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    boolean existsByUuid(final String uuid);

    @Query(value = "select u.email from organization o " +
            "         inner join user_role_organization uro on o.id = uro.organization_id" +
            "         inner join user_role ur on uro.id = ur.id" +
            "         inner join user_ u on ur.user_id = u.id " +
            "where o.uuid = ?1", nativeQuery = true)
    Optional<String> findOrganizationOwnerEmail(final String organizationUuid);
}
