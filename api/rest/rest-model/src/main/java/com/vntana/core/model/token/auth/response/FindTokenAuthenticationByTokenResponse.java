package com.vntana.core.model.token.auth.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel;
import com.vntana.core.model.token.auth.response.model.FindTokenAuthenticationByTokenResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan
 * Date: 7/15/20
 * Time: 2:28 AM
 */
public class FindTokenAuthenticationByTokenResponse extends AbstractResultResponseModel<FindTokenAuthenticationByTokenResponseModel, TokenAuthenticationErrorResponseModel> {
    public FindTokenAuthenticationByTokenResponse() {
    }

    public FindTokenAuthenticationByTokenResponse(final int httpStatusCode, final TokenAuthenticationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public FindTokenAuthenticationByTokenResponse(final int httpStatusCode, final List<TokenAuthenticationErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public FindTokenAuthenticationByTokenResponse(final FindTokenAuthenticationByTokenResponseModel response) {
        super(response);
    }
}