package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.EmptyResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 5:29 PM
 */
public class ResetUserPasswordResponse extends EmptyResultResponseModel<UserErrorResponseModel> {

    public ResetUserPasswordResponse() {
        super();
    }

    public ResetUserPasswordResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }
}
