package com.vntana.core.model.token.auth.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel;
import com.vntana.core.model.token.auth.response.model.AuthTokenIsExpiredResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:17 AM
 */
public class AuthTokenIsExpiredResultResponse extends AbstractResultResponseModel<AuthTokenIsExpiredResponseModel, AuthTokenErrorResponseModel> {

    public AuthTokenIsExpiredResultResponse() {
        super();
    }

    public AuthTokenIsExpiredResultResponse(final int httpStatusCode, final AuthTokenErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public AuthTokenIsExpiredResultResponse(final boolean expired) {
        super(new AuthTokenIsExpiredResponseModel(expired));
    }
}
