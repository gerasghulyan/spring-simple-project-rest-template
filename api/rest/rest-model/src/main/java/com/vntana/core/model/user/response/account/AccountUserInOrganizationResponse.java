package com.vntana.core.model.user.response.account;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.account.model.AccountUserInOrganizationResponseModel;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 04.12.2020
 * Time: 13:59
 */
public class AccountUserInOrganizationResponse extends AbstractResultResponseModel<AccountUserInOrganizationResponseModel, UserErrorResponseModel> {
    public AccountUserInOrganizationResponse() {
        super();
    }

    public AccountUserInOrganizationResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public AccountUserInOrganizationResponse(final int httpStatusCode, final List<UserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public AccountUserInOrganizationResponse(final AccountUserInOrganizationResponseModel response) {
        super(response);
    }
}

