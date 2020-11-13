package com.vntana.core.model.user.role.request;

import com.vntana.core.model.auth.response.UserRoleModel;

/**
 * Created by Diana Gevorgyan
 * Date: 11/5/20
 * Time: 11:38 AM
 */
public class UserRoleRevokeClientRequest extends AbstractUserRoleClientOrganizationAwareRequest {

    public UserRoleRevokeClientRequest() {
        super();
    }

    public UserRoleRevokeClientRequest(final String userUuid, final String clientUuid, final UserRoleModel userRole) {
        super(userUuid, clientUuid, userRole);
    }
}
