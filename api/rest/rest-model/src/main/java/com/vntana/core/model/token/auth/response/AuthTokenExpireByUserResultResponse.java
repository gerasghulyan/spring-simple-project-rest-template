package com.vntana.core.model.token.auth.response;

import com.vntana.commons.api.model.response.impl.EmptyResultResponseModel;
import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:50 AM
 */
public class AuthTokenExpireByUserResultResponse extends EmptyResultResponseModel<AuthTokenErrorResponseModel> {

    public AuthTokenExpireByUserResultResponse() {
        super();
    }

    public AuthTokenExpireByUserResultResponse(final int status, final AuthTokenErrorResponseModel error) {
        super(status, error);
    }
}
