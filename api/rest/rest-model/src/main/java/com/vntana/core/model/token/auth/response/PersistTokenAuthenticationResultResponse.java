package com.vntana.core.model.token.auth.response;

import com.vntana.commons.api.model.response.impl.EmptyResultResponseModel;
import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:31 AM
 */
public class PersistTokenAuthenticationResultResponse extends EmptyResultResponseModel<TokenAuthenticationErrorResponseModel> {

    public PersistTokenAuthenticationResultResponse() {
        super();
    }

    public PersistTokenAuthenticationResultResponse(final int status, final TokenAuthenticationErrorResponseModel error) {
        super(status, error);
    }
}
