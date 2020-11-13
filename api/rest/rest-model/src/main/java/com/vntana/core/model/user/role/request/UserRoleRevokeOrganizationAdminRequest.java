package com.vntana.core.model.user.role.request;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:28 AM
 */
public class UserRoleRevokeOrganizationAdminRequest extends AbstractUserRoleOrganizationAwareRequest {

    public UserRoleRevokeOrganizationAdminRequest() {
        super();
    }

    public UserRoleRevokeOrganizationAdminRequest(final String userUuid, final String organizationUuid) {
        super(userUuid, organizationUuid);
    }
}