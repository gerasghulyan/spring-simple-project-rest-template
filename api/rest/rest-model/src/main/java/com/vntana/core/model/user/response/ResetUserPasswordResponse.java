package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.model.ResetUserPasswordResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 5:29 PM
 */
public class ResetUserPasswordResponse extends AbstractResultResponseModel<ResetUserPasswordResponseModel, UserErrorResponseModel> {

    public ResetUserPasswordResponse() {
        super();
    }

    public ResetUserPasswordResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }

    public ResetUserPasswordResponse(final ResetUserPasswordResponseModel response) {
        super(response);
    }
}
