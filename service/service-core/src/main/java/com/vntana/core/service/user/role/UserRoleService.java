package com.vntana.core.service.user.role;

import com.vntana.core.domain.user.*;
import com.vntana.core.service.user.role.dto.UserGrantClientRoleDto;
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto;
import com.vntana.core.service.user.role.dto.UserRevokeOrganizationAdminRoleDto;
import com.vntana.core.service.user.role.dto.UserRevokeClientRoleDto;

import java.util.List;
import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 12:47 PM
 */

public interface UserRoleService {

    List<AbstractUserRole> findAllByOrganization(final String organizationUuid);

    List<AbstractUserRole> findClientsByOrganization(final String organizationUuid);

    List<AbstractClientOrganizationAwareUserRole> findClientOrganizationRoleByOrganizationAndUser(final String organizationUuid, final String userUuid);

    Optional<AbstractUserRole> findByOrganizationAndUser(final String organizationUuid, final String userUuid);

    Optional<AbstractUserRole> findByClientOrganizationAndUser(final String clientOrganizationUuid, final String userUuid);

    boolean existsByOrganizationAndUserAndRole(final String organizationUuid, final String userUuid, final UserRole userRole);

    UserOrganizationOwnerRole grantOrganizationOwnerRole(final UserGrantOrganizationRoleDto dto);

    UserOrganizationAdminRole grantOrganizationAdminRole(final UserGrantOrganizationRoleDto dto);

    UserSuperAdminRole grantSuperAdminRole(final String userUuid);
    
    AbstractUserRole grantClientRole(UserGrantClientRoleDto dto);
    
    void revokeOrganizationAdminRole(final UserRevokeOrganizationAdminRoleDto dto);

    void revokeClientRole(UserRevokeClientRoleDto dto);
}