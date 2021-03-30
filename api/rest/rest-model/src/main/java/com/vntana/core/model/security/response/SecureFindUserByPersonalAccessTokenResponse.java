package com.vntana.core.model.security.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.security.response.model.SecureFindUserByPersonalAccessTokenResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 3/29/21
 * Time: 1:17 AM
 */
public class SecureFindUserByPersonalAccessTokenResponse extends AbstractResultResponseModel<SecureFindUserByPersonalAccessTokenResponseModel, UserErrorResponseModel> {

    public SecureFindUserByPersonalAccessTokenResponse() {
        super();
    }

    public SecureFindUserByPersonalAccessTokenResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public SecureFindUserByPersonalAccessTokenResponse(final int httpStatusCode, final List<UserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public SecureFindUserByPersonalAccessTokenResponse(final SecureFindUserByPersonalAccessTokenResponseModel response) {
        super(response);
    }
}
