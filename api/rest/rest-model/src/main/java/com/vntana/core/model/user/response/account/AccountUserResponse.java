package com.vntana.core.model.user.response.account;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.account.model.AccountUserResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan.
 * Date: 10/18/19
 * Time: 5:07 PM
 */
public class AccountUserResponse extends AbstractResultResponseModel<AccountUserResponseModel, UserErrorResponseModel> {
    public AccountUserResponse() {
        super();
    }

    public AccountUserResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public AccountUserResponse(final int httpStatusCode, final List<UserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public AccountUserResponse(final AccountUserResponseModel response) {
        super(response);
    }
}
