package com.vntana.core.service.user.role;

import com.vntana.core.domain.user.AbstractUserRole;
import com.vntana.core.domain.user.UserOrganizationAdminRole;
import com.vntana.core.domain.user.UserOrganizationOwnerRole;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto;
import com.vntana.core.service.user.role.dto.UserRevokeOrganizationAdminRoleDto;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 12:47 PM
 */

public interface UserRoleService {

    List<AbstractUserRole> findAllByOrganizationUuid(final String organizationUuid);

    boolean existsByOrganizationAndUserAndRole(final String organizationUuid, final String userUuid, final UserRole userRole);

    UserOrganizationOwnerRole grantOrganizationOwnerRole(final UserGrantOrganizationRoleDto dto);

    UserOrganizationAdminRole grantOrganizationAdminRole(final UserGrantOrganizationRoleDto dto);

    void revokeOrganizationAdminRole(final UserRevokeOrganizationAdminRoleDto dto);
}
