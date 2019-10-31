package com.vntana.core.model.auth.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan.
 * Date: 10/1/19
 * Time: 6:44 PM
 */
public class SignInResultResponse extends AbstractResultResponseModel<SignInResponseModel, UserErrorResponseModel> {

    public SignInResultResponse() {
        super();
    }

    public SignInResultResponse(final boolean success, final List<UserErrorResponseModel> errors, final SignInResponseModel response) {
        super(success, errors, response);
    }

    public SignInResultResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }

    public SignInResultResponse(final SignInResponseModel response) {
        super(response);
    }
}
