package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.model.AccountUserResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan.
 * Date: 10/18/19
 * Time: 5:07 PM
 */
public class AccountUserResponse extends AbstractResultResponseModel<AccountUserResponseModel, UserErrorResponseModel> {
    public AccountUserResponse() {
    }

    public AccountUserResponse(final AccountUserResponseModel response) {
        super(response);
    }

    public AccountUserResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }
}
