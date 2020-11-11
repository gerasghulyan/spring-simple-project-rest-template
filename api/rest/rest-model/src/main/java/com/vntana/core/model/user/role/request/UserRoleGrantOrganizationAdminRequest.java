package com.vntana.core.model.user.role.request;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 4:24 PM
 */
public class UserRoleGrantOrganizationAdminRequest extends AbstractUserRoleGrantOrganizationAwareRequest {

    public UserRoleGrantOrganizationAdminRequest() {
        super();
    }

    public UserRoleGrantOrganizationAdminRequest(final String userUuid, final String organizationUuid) {
        super(userUuid, organizationUuid);
    }
}
