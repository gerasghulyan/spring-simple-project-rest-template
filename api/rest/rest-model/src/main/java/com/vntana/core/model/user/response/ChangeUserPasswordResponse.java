package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.model.ChangeUserPasswordResponseModel;

/**
 * Created by Manuk Gharslyan.
 * Date: 1/24/2020
 * Time: 10:21 AM
 */
public class ChangeUserPasswordResponse extends AbstractResultResponseModel<ChangeUserPasswordResponseModel, UserErrorResponseModel> {

    public ChangeUserPasswordResponse() {
    }

    public ChangeUserPasswordResponse(final UserErrorResponseModel error) {
        super(error);
    }

    public ChangeUserPasswordResponse(final String uuid) {
        super(new ChangeUserPasswordResponseModel(uuid));
    }
}