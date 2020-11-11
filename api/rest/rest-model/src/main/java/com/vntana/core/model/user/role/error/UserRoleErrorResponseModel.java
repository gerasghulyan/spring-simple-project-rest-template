package com.vntana.core.model.user.role.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:28 AM
 */
public enum UserRoleErrorResponseModel implements ErrorResponseModel {
    MISSING_ORGANIZATION_UUID,
    MISSING_USER_UUID,
    MISSING_CLIENT_ORGANIZATION_UUID,
    MISSING_CLIENT_ROLE,
    ORGANIZATION_NOT_FOUND,
    CLIENT_ORGANIZATION_NOT_FOUND,
    USER_NOT_FOUND,
    REQUESTED_ROLE_IS_ABSENT,
    REQUESTED_ROLE_ALREADY_GRANTED,
    REQUEST_ROLE_IS_NOT_CLIENT_RELATED
}
