package com.vntana.core.model.token.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import com.vntana.core.model.token.response.model.TokenCreateResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 11:34 AM
 */
public class TokenCreateResultResponse extends AbstractResultResponseModel<TokenCreateResponseModel, TokenErrorResponseModel> {

    public TokenCreateResultResponse() {
        super();
    }

    public TokenCreateResultResponse(final int httpStatusCode, final TokenErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public TokenCreateResultResponse(final String token) {
        super(new TokenCreateResponseModel(token));
    }
}
