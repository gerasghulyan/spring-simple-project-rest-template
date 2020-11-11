package com.vntana.core.model.user.role.request;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/27/20
 * Time: 2:24 PM
 */
public class UserRoleGrantSuperAdminRequest extends AbstractUserRoleGrantUserAwareRequest {

    public UserRoleGrantSuperAdminRequest() {
        super();
    }

    public UserRoleGrantSuperAdminRequest(final String userUuid) {
        super(userUuid);
    }
}
