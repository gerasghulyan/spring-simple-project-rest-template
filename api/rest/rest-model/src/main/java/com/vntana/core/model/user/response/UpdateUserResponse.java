package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.model.UpdateUserResponseModel;

/**
 * Created by Manuk Gharslyan.
 * Date: 1/23/2020
 * Time: 4:57 PM
 */
public class UpdateUserResponse extends AbstractResultResponseModel<UpdateUserResponseModel, UserErrorResponseModel> {

    public UpdateUserResponse() {
    }

    public UpdateUserResponse(final UserErrorResponseModel error) {
        super(error);
    }

    public UpdateUserResponse(final String uuid) {
        super(new UpdateUserResponseModel(uuid));
    }
}