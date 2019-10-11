package com.vntana.core.model.user.response;

import com.vntana.core.model.commons.response.result.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:44 PM
 */
public class UserCreateResultResponse extends AbstractResultResponseModel<UserCreateResponseModel, UserErrorResponseModel> {

    public UserCreateResultResponse() {
        super();
    }

    public UserCreateResultResponse(final boolean success, final List<UserErrorResponseModel> errors, final UserCreateResponseModel response) {
        super(success, errors, response);
    }

    public UserCreateResultResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }

    public UserCreateResultResponse(final UserCreateResponseModel response) {
        super(response);
    }
}
