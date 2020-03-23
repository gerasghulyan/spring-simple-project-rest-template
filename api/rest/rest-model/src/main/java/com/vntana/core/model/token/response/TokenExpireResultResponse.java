package com.vntana.core.model.token.response;

import com.vntana.commons.api.model.response.impl.EmptyResultResponseModel;
import com.vntana.core.model.token.error.TokenErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:50 AM
 */
public class TokenExpireResultResponse extends EmptyResultResponseModel<TokenErrorResponseModel> {

    public TokenExpireResultResponse() {
        super();
    }

    public TokenExpireResultResponse(final int status, final TokenErrorResponseModel error) {
        super(status, error);
    }
}
