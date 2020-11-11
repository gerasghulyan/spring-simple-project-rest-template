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
            "(select aur.id from UserOrganizationOwnerRole uoor join AbstractUserRole aur on aur.id = uoor.id where uoor.organization.uuid = :organizationUuid)" +
            " or role.id in " +
            "(select aur.id from UserOrganizationAdminRole uoar join AbstractUserRole aur on aur.id = uoar.id where uoar.organization.uuid = :organizationUuid)")
    List<AbstractUserRole> findAllByOrganization(@Param("organizationUuid") final String organizationUuid);

    @Query("select role from AbstractUserRole role where role.id in " +
            "(select aur.id from UserOrganizationOwnerRole uoor join AbstractUserRole aur on aur.id = uoor.id where uoor.organization.uuid = :organizationUuid and aur.user.uuid = :userUuid)" +
            " or role.id in " +
            "(select aur.id from UserOrganizationAdminRole uoar join AbstractUserRole aur on aur.id = uoar.id where uoar.organization.uuid = :organizationUuid and aur.user.uuid = :userUuid)")
    Optional<AbstractUserRole> findByOrganizationAndUser(@Param("organizationUuid") final String organizationUuid, @Param("userUuid") final String userUuid);

    @Query("select role from AbstractUserRole role where role.id in " +
            "(select aur.id from UserClientOrganizationAdminRole ucar join AbstractUserRole aur on aur.id = ucar.id where ucar.clientOrganization.organization.uuid = :organizationUuid)" +
            " or role.id in " +
            "(select aur.id from UserClientOrganizationContentManagerRole uccmr join AbstractUserRole aur on aur.id = uccmr.id where uccmr.clientOrganization.organization.uuid = :organizationUuid)" +
            " or role.id in " +
            "(select aur.id from UserClientOrganizationViewerRole ucvr join AbstractUserRole aur on aur.id = ucvr.id where ucvr.clientOrganization.organization.uuid = :organizationUuid)")
    List<AbstractUserRole> findAllOrganizationClientsByOrganization(@Param("organizationUuid") final String organizationUuid);

    @Query("select role from AbstractUserRole role where role.id in " +
            "(select aur.id from UserClientOrganizationAdminRole ucar join AbstractUserRole aur on aur.id = ucar.id where ucar.clientOrganization.organization.uuid = :organizationUuid and ucar.user.uuid = :userUuid)" +
            " or role.id in " +
            "(select aur.id from UserClientOrganizationContentManagerRole uccmr join AbstractUserRole aur on aur.id = uccmr.id where uccmr.clientOrganization.organization.uuid = :organizationUuid and uccmr.user.uuid = :userUuid)" +
            " or role.id in " +
            "(select aur.id from UserClientOrganizationViewerRole ucvr join AbstractUserRole aur on aur.id = ucvr.id where ucvr.clientOrganization.organization.uuid = :organizationUuid and ucvr.user.uuid = :userUuid)")
    List<AbstractUserRole> findAllOrganizationClientsByOrganizationAndUser(@Param("organizationUuid") final String organizationUuid,
                                                                           @Param("userUuid") final String userUuid);

    @Query("select role from AbstractUserRole role where role.id in " +
            "(select aur.id from UserClientOrganizationAdminRole ucar join AbstractUserRole aur on aur.id = ucar.id where ucar.clientOrganization.uuid = :clientOrganizationUuid and ucar.user.uuid = :userUuid)" +
            " or role.id in " +
            "(select aur.id from UserClientOrganizationContentManagerRole uccmr join AbstractUserRole aur on aur.id = uccmr.id where uccmr.clientOrganization.uuid = :clientOrganizationUuid and uccmr.user.uuid = :userUuid)" +
            " or role.id in " +
            "(select aur.id from UserClientOrganizationViewerRole ucvr join AbstractUserRole aur on aur.id = ucvr.id where ucvr.clientOrganization.uuid = :clientOrganizationUuid and ucvr.user.uuid = :userUuid)")
    Optional<AbstractUserRole> findByClientOrganizationAndUser(@Param("clientOrganizationUuid") final String clientOrganizationUuid, @Param("userUuid") final String userUuid);

    @Query("select aur from UserOrganizationAdminRole uoar join AbstractUserRole aur on aur.id = uoar.id where uoar.organization.uuid = :organizationUuid and uoar.user.uuid = :userUuid")
    Optional<AbstractUserRole> findAdminRoleByUserAndOrganization(@Param("userUuid") final String userUuid,
                                                                  @Param("organizationUuid") final String organizationUuid);
    
    @Query("select aur from UserClientOrganizationAdminRole uoar join AbstractUserRole aur on aur.id = uoar.id where uoar.clientOrganization.uuid = :clientOrganizationUuid and uoar.user.uuid = :userUuid")
    Optional<AbstractUserRole> findClientAdminRoleByUserAndClientOrganization(@Param("userUuid") final String userUuid,
                                                                              @Param("clientOrganizationUuid") final String clientOrganizationUuid);
    
    @Query("select aur from UserClientOrganizationContentManagerRole uoar join AbstractUserRole aur on aur.id = uoar.id where uoar.clientOrganization.uuid = :clientOrganizationUuid and uoar.user.uuid = :userUuid")
    Optional<AbstractUserRole> findClientContentManagerRoleByUserAndClientOrganization(@Param("userUuid") final String userUuid,
                                                                                       @Param("clientOrganizationUuid") final String clientOrganizationUuid);
    
    @Query("select aur from UserClientOrganizationViewerRole uoar join AbstractUserRole aur on aur.id = uoar.id where uoar.clientOrganization.uuid = :clientOrganizationUuid and uoar.user.uuid = :userUuid")
    Optional<AbstractUserRole> findClientViewerRoleByUserAndClientOrganization(@Param("userUuid") final String userUuid,
                                                                               @Param("clientOrganizationUuid") final String clientOrganizationUuid);    
    
}
