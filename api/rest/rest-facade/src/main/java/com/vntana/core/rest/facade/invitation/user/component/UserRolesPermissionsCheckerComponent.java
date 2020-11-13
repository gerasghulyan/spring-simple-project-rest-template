package com.vntana.core.rest.facade.invitation.user.component;

import com.vntana.core.model.auth.response.UserRoleModel;

/**
 * Created by Diana Gevorgyan
 * Date: 11/12/20
 * Time: 3:03 PM
 */
public interface UserRolesPermissionsCheckerComponent {

    boolean isPermittedToInvite(final UserRoleModel inviter, final UserRoleModel invited);
}
