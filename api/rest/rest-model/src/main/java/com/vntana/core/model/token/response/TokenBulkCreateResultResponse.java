package com.vntana.core.model.token.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import com.vntana.core.model.token.response.model.TokenBulkCreateResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 4:01 PM
 */
public class TokenBulkCreateResultResponse extends AbstractResultResponseModel<TokenBulkCreateResponseModel, TokenErrorResponseModel> {

    public TokenBulkCreateResultResponse() {
        super();
    }

    public TokenBulkCreateResultResponse(final int httpStatusCode, final TokenErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public TokenBulkCreateResultResponse(final List<String> tokens) {
        super(new TokenBulkCreateResponseModel(tokens));
    }
}
