package com.vntana.core.model.token.auth.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel;
import com.vntana.core.model.token.auth.response.model.AuthTokenFindByTokenResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan
 * Date: 7/15/20
 * Time: 2:28 AM
 */
public class AuthTokenFindByTokenResponse extends AbstractResultResponseModel<AuthTokenFindByTokenResponseModel, AuthTokenErrorResponseModel> {
    public AuthTokenFindByTokenResponse() {
    }

    public AuthTokenFindByTokenResponse(final int httpStatusCode, final AuthTokenErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public AuthTokenFindByTokenResponse(final int httpStatusCode, final List<AuthTokenErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public AuthTokenFindByTokenResponse(final AuthTokenFindByTokenResponseModel response) {
        super(response);
    }
}