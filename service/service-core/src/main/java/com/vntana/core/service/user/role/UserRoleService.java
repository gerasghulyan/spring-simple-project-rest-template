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

    List<AbstractUserRole> findAllByOrganizationUuid(final String organizationUuid);

    Optional<AbstractUserRole> findByOrganizationAndUser(final String organizationUuid, final String userUuid);

    boolean existsByOrganizationAndUserAndRole(final String organizationUuid, final String userUuid, final UserRole userRole);
    
    boolean existsByClientOrganizationAndUser(final String clientOrganizationUuid, final String userUuid);

    UserOrganizationOwnerRole grantOrganizationOwnerRole(final UserGrantOrganizationRoleDto dto);

    UserOrganizationAdminRole grantOrganizationAdminRole(final UserGrantOrganizationRoleDto dto);

    UserSuperAdminRole grantSuperAdminRole(final String userUuid);
    
    AbstractUserRole grantClientRole(UserGrantClientRoleDto dto);
    
    void revokeOrganizationAdminRole(final UserRevokeOrganizationAdminRoleDto dto);

    void revokeClientRole(UserRevokeClientRoleDto dto);
}