package com.vntana.core.models.user.request;

import com.vntana.core.api.models.response.error.ErrorResponseModel;
import com.vntana.core.models.commons.response.result.AbstractResultResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:44 PM
 */
public class UserCreateResultResponseModel extends AbstractResultResponseModel<UserCreateResponseModel, ErrorResponseModel> {

    private UserCreateResultResponseModel() {
        super();
    }

    public UserCreateResultResponseModel(final boolean success, final List<ErrorResponseModel> errors, final UserCreateResponseModel response) {
        super(success, errors, response);
    }

    public UserCreateResultResponseModel(final List<ErrorResponseModel> errors) {
        super(errors);
    }

    public UserCreateResultResponseModel(final UserCreateResponseModel response) {
        super(response);
    }
}
