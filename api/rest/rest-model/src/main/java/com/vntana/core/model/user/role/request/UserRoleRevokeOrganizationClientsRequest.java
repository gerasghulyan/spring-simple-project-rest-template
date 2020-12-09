package com.vntana.core.model.user.role.request;

/**
 * Created by Diana Gevorgyan
 * Date: 11/5/20
 * Time: 11:38 AM
 */
public class UserRoleRevokeOrganizationClientsRequest extends AbstractUserRoleOrganizationAwareRequest {

    public UserRoleRevokeOrganizationClientsRequest() {
        super();
    }

    public UserRoleRevokeOrganizationClientsRequest(final String userUuid, final String organizationUuid) {
        super(userUuid, organizationUuid);
    }
}