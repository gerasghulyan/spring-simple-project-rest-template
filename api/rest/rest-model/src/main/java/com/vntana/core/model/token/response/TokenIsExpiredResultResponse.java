package com.vntana.core.model.token.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import com.vntana.core.model.token.response.model.TokenIsExpiredResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:17 AM
 */
public class TokenIsExpiredResultResponse extends AbstractResultResponseModel<TokenIsExpiredResponseModel, TokenErrorResponseModel> {

    public TokenIsExpiredResultResponse() {
        super();
    }

    public TokenIsExpiredResultResponse(final int httpStatusCode, final TokenErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public TokenIsExpiredResultResponse(final boolean expired) {
        super(new TokenIsExpiredResponseModel(expired));
    }
}
