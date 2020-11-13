package com.vntana.core.model.token.auth.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel;
import com.vntana.core.model.token.auth.response.model.IsExpiredTokenAuthenticationResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:17 AM
 */
public class IsExpiredTokenAuthenticationResultResponse extends AbstractResultResponseModel<IsExpiredTokenAuthenticationResponseModel, TokenAuthenticationErrorResponseModel> {

    public IsExpiredTokenAuthenticationResultResponse() {
        super();
    }

    public IsExpiredTokenAuthenticationResultResponse(final int httpStatusCode, final TokenAuthenticationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public IsExpiredTokenAuthenticationResultResponse(final boolean expired) {
        super(new IsExpiredTokenAuthenticationResponseModel(expired));
    }
}
