package com.vntana.core.rest.facade.invitation.user.component;

import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.role.request.UserUpdateOrganizationClientsRolesRequest;
import com.vntana.core.model.user.role.request.UserUpdateOrganizationRoleRequest;

/**
 * Created by Diana Gevorgyan
 * Date: 11/12/20
 * Time: 3:03 PM
 */
public interface UserRolesPermissionsCheckerComponent {

    boolean isPermittedToInvite(final UserRoleModel inviter, final UserRoleModel invited);
    
    boolean isPermittedToGrant(final UserRoleModel granter, final UserRoleModel granted);

    boolean isPermittedToUpdateOrganizationRole(final UserUpdateOrganizationRoleRequest request);
    
    boolean isPermittedClientUserToUpdateClientRole(final UserUpdateOrganizationClientsRolesRequest request);
}
