package com.vntana.core.model.security.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.security.response.model.PersonalAccessTokenExpireResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 3/29/21
 * Time: 12:24 PM
 */
public class PersonalAccessTokenExpireResponse extends AbstractResultResponseModel<PersonalAccessTokenExpireResponseModel, UserErrorResponseModel> {

    public PersonalAccessTokenExpireResponse() {
    }

    public PersonalAccessTokenExpireResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public PersonalAccessTokenExpireResponse(final int httpStatusCode, final List<UserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public PersonalAccessTokenExpireResponse(final PersonalAccessTokenExpireResponseModel response) {
        super(response);
    }
}
