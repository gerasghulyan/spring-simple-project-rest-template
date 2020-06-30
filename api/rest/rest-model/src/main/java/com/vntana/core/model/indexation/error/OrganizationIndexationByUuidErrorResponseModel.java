package com.vntana.core.model.indexation.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Geras Ghulyan.
 * Date: 10/31/19
 * Time: 5:02 PM
 */
public enum OrganizationIndexationByUuidErrorResponseModel implements ErrorResponseModel {
    INDEXATION_ERROR,
    MISSING_ORGANIZATION_UUID,
    ORGANIZATION_NOT_FOUND
}