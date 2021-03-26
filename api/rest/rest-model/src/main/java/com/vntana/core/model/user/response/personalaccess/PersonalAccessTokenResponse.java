package com.vntana.core.model.user.response.personalaccess;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 1:21 AM
 */
public class PersonalAccessTokenResponse extends AbstractResultResponseModel<PersonalAccessTokenResponseModel, UserErrorResponseModel> {

    public PersonalAccessTokenResponse() {
    }

    public PersonalAccessTokenResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public PersonalAccessTokenResponse(final int httpStatusCode, final List<UserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public PersonalAccessTokenResponse(final String token, final String userUuid) {
        super(new PersonalAccessTokenResponseModel(token, userUuid));
    }
}
