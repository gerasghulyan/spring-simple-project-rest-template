package com.vntana.core.rest.facade.user.role;

import com.vntana.core.model.user.role.request.*;
import com.vntana.core.model.user.role.response.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:25 AM
 */
public interface UserRoleServiceFacade {

    UserRoleGrantSuperAdminResponse grantSuperAdmin(final UserRoleGrantSuperAdminRequest request);

    UserRoleGrantOrganizationAdminResponse grantOrganizationAdminRole(final UserRoleGrantOrganizationAdminRequest request);

    UserRoleGrantClientOrganizationResponse grantClientRole(final UserRoleGrantClientOrganizationRequest request);

    UserRoleRevokeOrganizationAdminResponse revokeOrganizationAdminRole(final UserRoleRevokeOrganizationAdminRequest request);

    UserRoleRevokeClientResponse revokeClientRole(final UserRoleRevokeClientRequest request);

    UserRoleRevokeOrganizationClientsResponse revokeOrganizationClientsRoles(final UserRoleRevokeOrganizationClientsRequest request);

    UserUpdateRolesResponse updateUserOrganizationRole(final UserUpdateOrganizationRoleRequest request);

    UserUpdateRolesResponse updateUserOrganizationClientsRoles(final UserUpdateOrganizationClientsRolesRequest request);
}
