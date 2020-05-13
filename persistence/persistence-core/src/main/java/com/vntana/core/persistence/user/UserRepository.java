package com.vntana.core.persistence.user;

import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 6:27 PM
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(final String email);

    boolean existsByEmail(final String email);

    Optional<User> findByUuid(final String uuid);

    boolean existsByUuid(final String uuid);

    // TODO: 5/12/2020 from UserOrganizationOwnerRole very simple case, should be more generic
    @Query("select u from UserOrganizationOwnerRole uro join uro.user u where uro.userRole = :userRole and uro.organization.uuid = :organizationUuid")
    List<User> findByRoleAndOrganizationUuid(@Param("userRole") final UserRole userRole, @Param("organizationUuid") final String organizationUuid);

    // TODO: 5/12/2020 this is very complex query 
    @Query("select u from User u where u.id in (select uoor.user.id from UserOrganizationOwnerRole uoor where uoor.user.email = :email and uoor.organization.uuid = :organizationUuid)" +
            " or u.id in (select uoar.user.id from UserOrganizationAdminRole uoar where uoar.user.email = :email and uoar.organization.uuid = :organizationUuid)")
    Optional<User> findByEmailAndOrganizationUuid(@Param("email") final String email, @Param("organizationUuid") final String organizationUuid);
}
