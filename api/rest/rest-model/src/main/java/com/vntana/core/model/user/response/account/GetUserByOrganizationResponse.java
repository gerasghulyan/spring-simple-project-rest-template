package com.vntana.core.model.user.response.account;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.account.model.GetUserByOrganizationResponseModel;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 04.12.2020
 * Time: 13:59
 */
public class GetUserByOrganizationResponse extends AbstractResultResponseModel<GetUserByOrganizationResponseModel, UserErrorResponseModel> {
    public GetUserByOrganizationResponse() {
        super();
    }

    public GetUserByOrganizationResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public GetUserByOrganizationResponse(final int httpStatusCode, final List<UserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public GetUserByOrganizationResponse(final GetUserByOrganizationResponseModel response) {
        super(response);
    }
}

