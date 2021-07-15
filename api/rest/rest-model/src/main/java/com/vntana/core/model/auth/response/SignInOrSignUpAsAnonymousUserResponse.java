package com.vntana.core.model.auth.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

/**
 * Created by Diana Gevorgyan
 * Date: 7/14/2021
 * Time: 4:52 PM
 */
public class SignInOrSignUpAsAnonymousUserResponse extends AbstractResultResponseModel<SignInOrSignUpAsAnonymousUserResponseModel, UserErrorResponseModel> {

    public SignInOrSignUpAsAnonymousUserResponse() {
    }

    public SignInOrSignUpAsAnonymousUserResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public SignInOrSignUpAsAnonymousUserResponse(final SignInOrSignUpAsAnonymousUserResponseModel response) {
        super(response);
    }
}
