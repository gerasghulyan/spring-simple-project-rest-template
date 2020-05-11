package com.vntana.core.service.user.role.exception;

import static java.lang.String.format;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/8/20
 * Time: 6:25 PM
 */
public class UserOrganizationAdminRoleNotFoundException extends RuntimeException {

    private final String userUuid;
    private final String organizationUuid;

    public UserOrganizationAdminRoleNotFoundException(final String userUuid, final String organizationUuid) {
        super(format("UserOrganizationAdminRole not found for user %s and organization %s", userUuid, organizationUuid));
        this.userUuid = userUuid;
        this.organizationUuid = organizationUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}
