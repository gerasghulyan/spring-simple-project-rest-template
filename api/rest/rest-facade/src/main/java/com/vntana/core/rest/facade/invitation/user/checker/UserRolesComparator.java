package com.vntana.core.rest.facade.invitation.user.checker;

import com.vntana.core.model.auth.response.UserRoleModel;

/**
 * Created by Diana Gevorgyan
 * Date: 11/10/20
 * Time: 6:10 PM
 */
public interface UserRolesComparator {

    boolean compare(final UserRoleModel userRole1, final UserRoleModel userRole2);
}
