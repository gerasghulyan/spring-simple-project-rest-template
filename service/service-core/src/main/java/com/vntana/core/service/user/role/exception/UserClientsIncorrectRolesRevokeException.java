package com.vntana.core.service.user.role.exception;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by Vardan Aivazian
 * Date: 05.11.2020
 * Time: 11:52
 */
public class UserClientsIncorrectRolesRevokeException extends RuntimeException {

    private final String userUuid;
    private final List<String> clientOrganizationUuids;

    public UserClientsIncorrectRolesRevokeException(final String userUuid, final List<String> clientOrganizationUuids) {
        super(format("UserClientRoles not found for user %s and clientOrganizationUuids %s", userUuid, Arrays.toString(clientOrganizationUuids.toArray())));
        this.userUuid = userUuid;
        this.clientOrganizationUuids = clientOrganizationUuids;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public List<String> getClientOrganizationUuids() {
        return clientOrganizationUuids;
    }
}