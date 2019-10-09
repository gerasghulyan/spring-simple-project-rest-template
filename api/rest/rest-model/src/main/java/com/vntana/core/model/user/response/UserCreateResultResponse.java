package com.vntana.core.model.user.response;

import com.vntana.core.api.models.response.error.ErrorResponseModel;
import com.vntana.core.model.commons.response.result.AbstractResultResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:44 PM
 */
public class UserCreateResultResponse extends AbstractResultResponseModel<UserCreateResponseModel, ErrorResponseModel> {

    private UserCreateResultResponse() {
        super();
    }

    public UserCreateResultResponse(final boolean success, final List<ErrorResponseModel> errors, final UserCreateResponseModel response) {
        super(success, errors, response);
    }

    public UserCreateResultResponse(final List<ErrorResponseModel> errors) {
        super(errors);
    }

    public UserCreateResultResponse(final UserCreateResponseModel response) {
        super(response);
    }
}
