package com.vntana.core.model.catalog.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 8:50 AM
 */
public enum FacebookCatalogSettingErrorResponseModel implements ErrorResponseModel {
    MISSING_ORGANIZATION_UUID,
    ORGANIZATION_NOT_FOUND,
    MISSING_NAME,
    MISSING_CATALOG_ID,
    FACEBOOK_CATALOG_SETTING_NOT_FOUND,
    FACEBOOK_CATALOG_SETTINGS_CANNOT_BE_NULL,
    MISSING_SYSTEM_USER_TOKEN
}
