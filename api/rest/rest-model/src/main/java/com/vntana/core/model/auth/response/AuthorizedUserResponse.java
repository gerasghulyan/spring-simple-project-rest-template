package com.vntana.core.model.auth.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan.
 * Date: 10/1/19
 * Time: 6:44 PM
 */
public class AuthorizedUserResponse extends AbstractResultResponseModel<AuthorizedUserResponseModel, UserErrorResponseModel> {

    public AuthorizedUserResponse() {
        super();
    }

    public AuthorizedUserResponse(final boolean success, final List<UserErrorResponseModel> errors, final AuthorizedUserResponseModel response) {
        super(success, errors, response);
    }

    public AuthorizedUserResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }

    public AuthorizedUserResponse(final AuthorizedUserResponseModel response) {
        super(response);
    }
}
