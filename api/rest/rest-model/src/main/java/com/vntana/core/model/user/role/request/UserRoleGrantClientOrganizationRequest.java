package com.vntana.core.model.user.role.request;

import com.vntana.core.model.auth.response.UserRoleModel;

/**
 * Created by Diana Gevorgyan
 * Date: 11/5/20
 * Time: 10:10 AM
 */
public class UserRoleGrantClientOrganizationRequest extends AbstractUserRoleClientOrganizationAwareRequest {

    public UserRoleGrantClientOrganizationRequest() {
        super();
    }

    public UserRoleGrantClientOrganizationRequest(final String userUuid, final String clientUuid, final UserRoleModel userRole) {
        super(userUuid, clientUuid, userRole);
    }
}