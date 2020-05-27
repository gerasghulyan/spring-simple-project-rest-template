package com.vntana.core.rest.facade.user.role;

import com.vntana.core.model.user.role.request.UserRoleGrantOrganizationAdminRequest;
import com.vntana.core.model.user.role.request.UserRoleGrantSuperAdminRequest;
import com.vntana.core.model.user.role.request.UserRoleRevokeOrganizationAdminRequest;
import com.vntana.core.model.user.role.response.UserRoleGrantOrganizationAdminResponse;
import com.vntana.core.model.user.role.response.UserRoleGrantSuperAdminResponse;
import com.vntana.core.model.user.role.response.UserRoleRevokeOrganizationAdminResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:25 AM
 */
public interface UserRoleServiceFacade {

    UserRoleGrantSuperAdminResponse grantSuperAdmin(final UserRoleGrantSuperAdminRequest request);

    UserRoleGrantOrganizationAdminResponse grantOrganizationAdminRole(final UserRoleGrantOrganizationAdminRequest request);

    UserRoleRevokeOrganizationAdminResponse revokeOrganizationAdminRole(final UserRoleRevokeOrganizationAdminRequest request);
}
