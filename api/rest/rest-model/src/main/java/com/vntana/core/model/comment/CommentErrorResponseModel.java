package com.vntana.core.model.comment;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:07 PM
 */
public enum CommentErrorResponseModel implements ErrorResponseModel {
    MISSING_UUID,
    MISSING_PAGE,
    MISSING_SIZE,
    MISSING_USER_UUID,
    MISSING_PRODUCT_UUID,
    MISSING_MESSAGE,
    USER_NOT_FOUND,
    COMMENT_NOT_FOUND,
    USER_ACCESS_DENIED
}
