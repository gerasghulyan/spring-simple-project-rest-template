package com.vntana.core.model.whitelist.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 5:45 PM
 */
public enum WhitelistIpErrorResponseModel implements ErrorResponseModel {
    MISSING_WHITELIST_IPS,
    MISSING_ORGANIZATION_UUID,
    MISSING_LABEL,
    MISSING_IP,
    ORGANIZATION_NOT_FOUND,
    INVALID_IP
}
