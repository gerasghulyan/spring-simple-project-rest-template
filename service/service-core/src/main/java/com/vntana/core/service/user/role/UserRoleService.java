package com.vntana.core.service.user.role;

import com.vntana.core.domain.user.*;
import com.vntana.core.service.user.role.dto.UserGrantOrganizationClientRoleDto;
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto;
import com.vntana.core.service.user.role.dto.UserRevokeOrganizationAdminRoleDto;
import com.vntana.core.service.user.role.dto.UserRevokeOrganizationClientRoleDto;

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

    UserOrganizationOwnerRole grantOrganizationOwnerRole(final UserGrantOrganizationRoleDto dto);

    UserOrganizationAdminRole grantOrganizationAdminRole(final UserGrantOrganizationRoleDto dto);

    UserSuperAdminRole grantSuperAdminRole(final String userUuid);
    
    AbstractUserRole grantOrganizationClientRole(UserGrantOrganizationClientRoleDto dto);
    
    void revokeOrganizationAdminRole(final UserRevokeOrganizationAdminRoleDto dto);

    void revokeOrganizationClientRole(UserRevokeOrganizationClientRoleDto dto);
}