package com.vntana.core.model.user.role.request;

/**
 * Created by Diana Gevorgyan
 * Date: 11/5/20
 * Time: 10:10 AM
 */
public class UserRoleGrantClientAdminOrganizationRequest extends AbstractUserRoleGrantClientOrganizationAwareRequest {

    public UserRoleGrantClientAdminOrganizationRequest() {
        super();
    }

    public UserRoleGrantClientAdminOrganizationRequest(final String userUuid, final String clientOrganizationUuid) {
        super(userUuid, clientOrganizationUuid);
    }
}