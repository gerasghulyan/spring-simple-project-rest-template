package com.vntana.core.persistence.organization;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query("select uoor.user.email from UserOrganizationOwnerRole uoor where uoor.organization.uuid = :organizationUuid")
    Optional<String> findOrganizationOwnerEmail(@Param("organizationUuid") final String organizationUuid);


    @Query("select o from Organization o where o.id in (select uoor.organization.id from UserOrganizationOwnerRole uoor where uoor.user.uuid = :userUuid and uoor.userRole = :userRole)" +
            " or o.id in (select uoar.organization.id from UserOrganizationAdminRole uoar where uoar.user.uuid = :userUuid and uoar.userRole = :userRole)")
    List<Organization> findUserOrganizationsByUserUuidAndRole(@Param("userUuid") final String userUuid, @Param("userRole") final UserRole userRole);
}
