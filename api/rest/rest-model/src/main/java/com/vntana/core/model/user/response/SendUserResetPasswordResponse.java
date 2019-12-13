package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.EmptyResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 12:40 PM
 */
public class SendUserResetPasswordResponse extends EmptyResultResponseModel<UserErrorResponseModel> {

    public SendUserResetPasswordResponse() {
        super();
    }

    public SendUserResetPasswordResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }
}
