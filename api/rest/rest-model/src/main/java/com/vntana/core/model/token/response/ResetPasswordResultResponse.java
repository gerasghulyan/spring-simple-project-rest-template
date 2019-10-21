package com.vntana.core.model.token.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.commons.api.model.response.impl.EmptyResponseModel;
import com.vntana.core.model.token.error.TokenErrorResponseModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:18 PM
 */
public class ResetPasswordResultResponse extends AbstractResultResponseModel<EmptyResponseModel, TokenErrorResponseModel> {
    public ResetPasswordResultResponse() {
        super();
        success(true);
        errors(Collections.emptyList());
    }

    public ResetPasswordResultResponse(final List<TokenErrorResponseModel> errors) {
        super(errors);
    }
}
