package com.vntana.core.model.user.response.personalaccess;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.token.auth.response.model.FindTokenAuthenticationByTokenResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 3:38 PM
 */
public class FindByPersonalAccessTokenResponseModel extends AbstractResultResponseModel<FindTokenAuthenticationByTokenResponseModel, UserErrorResponseModel> {

    public FindByPersonalAccessTokenResponseModel() {
    }

    public FindByPersonalAccessTokenResponseModel(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public FindByPersonalAccessTokenResponseModel(final FindTokenAuthenticationByTokenResponseModel response) {
        super(response);
    }
}
