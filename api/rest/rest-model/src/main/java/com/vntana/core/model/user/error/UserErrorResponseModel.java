package com.vntana.core.model.user.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 4:05 PM
 */
public enum UserErrorResponseModel implements ErrorResponseModel {
    MISSING_CLIENT_NAME,
    MISSING_CLIENT_SLUG,
    MISSING_CLIENT,
    MISSING_FULL_NAME,
    MISSING_EMAIL,
    MISSING_PASSWORD,
    MISSING_ORGANIZATION,
    MISSING_ORGANIZATION_SLUG,
    MISSING_UUID,
    MISSING_UUIDS,
    MISSING_USER_ROLE,
    NOT_FOUND_FOR_EMAIL,
    NOT_FOUND_FOR_ORGANIZATION,
    NOT_FOUND_FOR_CLIENT_ORGANIZATION,
    NOT_FOUND_FOR_USERS_UUIDS,
    NOT_FOUND_FOR_UUID,
    NOT_FOUND_FOR_ROLE,
    ORGANIZATION_NOT_FOUND,
    SIGN_UP_WITH_EXISTING_EMAIL,
    INVALID_EMAIL_FORMAT,
    USER_ALREADY_VERIFIED,
    MISSING_VERIFICATION_TOKEN,
    MISSING_RESET_PASSWORD_TOKEN,
    INVALID_RESET_PASSWORD_TOKEN,
    USER_NOT_FOUND,
    PASSWORD_MISMATCH,
    INVALID_VERIFICATION_TOKEN,
    ORGANIZATION_ROLE_CONFLICT,
    NOT_A_SUPER_ADMIN,
    MISSING_TOKEN,
    MISSING_PROMPTING_USER_NAME,
    MISSING_MENTIONED_USER_NAME,
    MISSING_ENTITY_TYPE,
    MISSING_ENTITY_UUID,
    MISSING_PRODUCT_NAME
}
