package com.vntana.core.model.annotation;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 17:59
 */
public enum AnnotationErrorResponseModel implements ErrorResponseModel {
    MISSING_UUID,
    MISSING_USER_UUID,
    MISSING_PRODUCT_UUID,
    MISSING_TEXT,
    MISSING_RESOLVED,
    MISSING_NUMBER,
    MISSING_DIMENSION,
    NOT_POSITIVE_NUMBER,
    USER_NOT_FOUND,
    ANNOTATION_NOT_FOUND,
    USER_ACCESS_DENIED,
    ALREADY_DELETED
}
