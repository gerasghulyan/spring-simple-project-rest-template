package com.vntana.core.service.user.role.exception;

import com.vntana.core.service.user.role.dto.UserClientRole;

import static java.lang.String.format;

/**
 * Created by Vardan Aivazian
 * Date: 05.11.2020
 * Time: 11:52
 */
public class UserClientRoleNotFoundException extends RuntimeException {

    private final UserClientRole clientRole;
    private final String userUuid;
    private final String clientOrganizationUuid;

    public UserClientRoleNotFoundException(
            final UserClientRole clientRole,
            final String userUuid,
            final String clientOrganizationUuid
    ) {
        super(format("UserClientRole %s not found for user %s and clientOrganizationUuid %s", clientRole, userUuid, clientOrganizationUuid));
        this.clientRole = clientRole;
        this.userUuid = userUuid;
        this.clientOrganizationUuid = clientOrganizationUuid;
    }

    public UserClientRole getClientRole() {
        return clientRole;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getClientOrganizationUuid() {
        return clientOrganizationUuid;
    }
}
