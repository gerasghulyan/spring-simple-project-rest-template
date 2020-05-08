package com.vntana.core.persistence.user.role;

import com.vntana.core.domain.user.AbstractUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 12:44 PM
 */
public interface UserRoleRepository extends JpaRepository<AbstractUserRole, Long> {

    @Query("select role from AbstractUserRole role where role.id in " +
            "(Select aur.id from UserClientOrganizationRole ucor join AbstractUserRole aur on aur.id = ucor.id where ucor.clientOrganization.organization.uuid = :organizationUuid)" +
            " or role.id in " +
            "(Select aur.id from UserOrganizationOwnerRole uoor join AbstractUserRole aur on aur.id = uoor.id where uoor.organization.uuid = :organizationUuid)")
    List<AbstractUserRole> findAllByOrganizationUuid(@Param("organizationUuid") final String organizationUuid);
}
