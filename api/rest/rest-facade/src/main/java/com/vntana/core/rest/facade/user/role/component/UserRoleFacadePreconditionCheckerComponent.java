package com.vntana.core.rest.facade.user.role.component;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.request.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:34 AM
 */
public interface UserRoleFacadePreconditionCheckerComponent {

    SingleErrorWithStatus<UserRoleErrorResponseModel> checkGrantSuperAdmin(final UserRoleGrantSuperAdminRequest request);

    SingleErrorWithStatus<UserRoleErrorResponseModel> checkGrantOrganizationAdminRole(final UserRoleGrantOrganizationAdminRequest request);

    SingleErrorWithStatus<UserRoleErrorResponseModel> checkGrantClientRole(final UserRoleGrantClientOrganizationRequest request);

    SingleErrorWithStatus<UserRoleErrorResponseModel> checkRevokeOrganizationAdminRole(final UserRoleRevokeOrganizationAdminRequest request);

    SingleErrorWithStatus<UserRoleErrorResponseModel> checkRevokeClientRole(final UserRoleRevokeClientRequest request);

    SingleErrorWithStatus<UserRoleErrorResponseModel> checkRevokeOrganizationClientsRoles(final UserRoleRevokeOrganizationClientsRequest request);
}
