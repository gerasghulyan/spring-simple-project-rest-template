package com.vntana.core.service.user.role.exception;

import com.vntana.core.domain.user.UserRole;

import static java.lang.String.format;

/**
 * Created by Vardan Aivazian
 * Date: 05.11.2020
 * Time: 11:52
 */
public class UserClientRoleNotFoundException extends RuntimeException {

    private final UserRole clientRole;
    private final String userUuid;
    private final String clientOrganizationUuid;

    public UserClientRoleNotFoundException(
            final UserRole clientRole,
            final String userUuid,
            final String clientOrganizationUuid
    ) {
        super(format("UserClientRole %s not found for user %s and clientOrganizationUuid %s", clientRole, userUuid, clientOrganizationUuid));
        this.clientRole = clientRole;
        this.userUuid = userUuid;
        this.clientOrganizationUuid = clientOrganizationUuid;
    }

    public UserRole getClientRole() {
        return clientRole;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getClientOrganizationUuid() {
        return clientOrganizationUuid;
    }
}
