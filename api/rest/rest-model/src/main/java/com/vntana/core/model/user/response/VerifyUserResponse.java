package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.model.VerifyUserResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/11/19
 * Time: 1:29 PM
 */
public class VerifyUserResponse extends AbstractResultResponseModel<VerifyUserResponseModel, UserErrorResponseModel> {

    public VerifyUserResponse() {
        super();
    }

    public VerifyUserResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }

    public VerifyUserResponse(final String uuid) {
        super(new VerifyUserResponseModel(uuid));
    }

}
