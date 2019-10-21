package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.model.CreateUserResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:44 PM
 */
public class CreateUserResultResponse extends AbstractResultResponseModel<CreateUserResponseModel, UserErrorResponseModel> {

    public CreateUserResultResponse() {
        super();
    }

    public CreateUserResultResponse(final CreateUserResponseModel response) {
        super(response);
    }

    public CreateUserResultResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }
}
