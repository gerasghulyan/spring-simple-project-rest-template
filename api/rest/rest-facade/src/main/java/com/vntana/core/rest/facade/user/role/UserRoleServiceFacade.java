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

    UserRoleGrantClientAdminResponse grantClientAdminRole(final UserRoleGrantClientAdminRequest request);

    UserRoleRevokeOrganizationAdminResponse revokeOrganizationAdminRole(final UserRoleRevokeOrganizationAdminRequest request);

    UserRoleRevokeClientAdminResponse revokeClientAdminRole(final UserRoleRevokeClientAdminRequest request);
}
