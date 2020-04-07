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

    @Query("select u from UserOrganizationRole uro join uro.user u where uro.userRole = :userRole and uro.organization.uuid = :organizationUuid")
    List<User> findByRoleAndOrganizationUuid(@Param("userRole") final UserRole userRole, @Param("organizationUuid") final String organizationUuid);
}
