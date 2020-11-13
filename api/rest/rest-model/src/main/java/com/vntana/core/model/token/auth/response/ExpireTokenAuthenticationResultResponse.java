package com.vntana.core.model.token.auth.response;

import com.vntana.commons.api.model.response.impl.EmptyResultResponseModel;
import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:50 AM
 */
public class ExpireTokenAuthenticationResultResponse extends EmptyResultResponseModel<TokenAuthenticationErrorResponseModel> {

    public ExpireTokenAuthenticationResultResponse() {
        super();
    }

    public ExpireTokenAuthenticationResultResponse(final int status, final TokenAuthenticationErrorResponseModel error) {
        super(status, error);
    }
}
