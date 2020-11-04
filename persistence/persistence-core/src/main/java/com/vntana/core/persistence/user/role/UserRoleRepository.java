package com.vntana.core.persistence.user.role;

import com.vntana.core.domain.user.AbstractUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 12:44 PM
 */
public interface UserRoleRepository extends JpaRepository<AbstractUserRole, Long> {

    @Query("select role from AbstractUserRole role where role.id in " +
            "(select aur.id from UserClientAdminRole ucor join AbstractUserRole aur on aur.id = ucor.id where ucor.clientOrganization.organization.uuid = :organizationUuid)" +
            " or role.id in " +
            "(select aur.id from UserClientContentManagerRole ucor join AbstractUserRole aur on aur.id = ucor.id where ucor.clientOrganization.organization.uuid = :organizationUuid)" +
            " or role.id in " +
            "(select aur.id from UserClientViewerRole ucor join AbstractUserRole aur on aur.id = ucor.id where ucor.clientOrganization.organization.uuid = :organizationUuid)" +
            " or role.id in " +
            "(select aur.id from UserOrganizationOwnerRole uoor join AbstractUserRole aur on aur.id = uoor.id where uoor.organization.uuid = :organizationUuid)" +
            " or role.id in " +
            "(select aur.id from UserOrganizationAdminRole uoar join AbstractUserRole aur on aur.id = uoar.id where uoar.organization.uuid = :organizationUuid)")
    List<AbstractUserRole> findAllByOrganizationUuid(@Param("organizationUuid") final String organizationUuid);

    @Query("select role from AbstractUserRole role where role.id in " +
            "(select aur.id from UserClientAdminRole ucor join AbstractUserRole aur on aur.id = ucor.id where ucor.clientOrganization.organization.uuid = :organizationUuid and aur.user.uuid = :userUuid)" +
            " or role.id in " +
            "(select aur.id from UserClientContentManagerRole ucor join AbstractUserRole aur on aur.id = ucor.id where ucor.clientOrganization.organization.uuid = :organizationUuid and aur.user.uuid = :userUuid)" +
            " or role.id in " +
            "(select aur.id from UserClientViewerRole ucor join AbstractUserRole aur on aur.id = ucor.id where ucor.clientOrganization.organization.uuid = :organizationUuid and aur.user.uuid = :userUuid)" +
            " or role.id in " +
            "(select aur.id from UserOrganizationOwnerRole uoor join AbstractUserRole aur on aur.id = uoor.id where uoor.organization.uuid = :organizationUuid and aur.user.uuid = :userUuid)" +
            " or role.id in " +
            "(select aur.id from UserOrganizationAdminRole uoar join AbstractUserRole aur on aur.id = uoar.id where uoar.organization.uuid = :organizationUuid and aur.user.uuid = :userUuid)")
    Optional<AbstractUserRole> findAllByOrganizationAndUser(@Param("organizationUuid") final String organizationUuid, @Param("userUuid") final String userUuid);

    @Query("select aur from UserOrganizationAdminRole uoar join AbstractUserRole aur on aur.id = uoar.id where uoar.organization.uuid = :organizationUuid and uoar.user.uuid = :userUuid")
    Optional<AbstractUserRole> findAdminRoleByUserAndOrganization(@Param("userUuid") final String userUuid,
                                                                  @Param("organizationUuid") final String organizationUuid);
}
