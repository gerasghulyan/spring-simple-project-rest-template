package com.vntana.core.model.invitation.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 10:27 AM
 */
public enum InvitationErrorResponseModel implements ErrorResponseModel {
    MISSING_TOKEN,
    MISSING_EMAIL,
    USER_ALREADY_EXISTS
}
