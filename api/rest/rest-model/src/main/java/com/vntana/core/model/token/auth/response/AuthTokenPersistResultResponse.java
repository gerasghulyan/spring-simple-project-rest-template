package com.vntana.core.model.token.auth.response;

import com.vntana.commons.api.model.response.impl.EmptyResultResponseModel;
import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:31 AM
 */
public class AuthTokenPersistResultResponse extends EmptyResultResponseModel<AuthTokenErrorResponseModel> {

    public AuthTokenPersistResultResponse() {
        super();
    }

    public AuthTokenPersistResultResponse(final int status, final AuthTokenErrorResponseModel error) {
        super(status, error);
    }
}
