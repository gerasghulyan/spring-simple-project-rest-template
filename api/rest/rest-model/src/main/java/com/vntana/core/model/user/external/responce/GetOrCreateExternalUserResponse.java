package com.vntana.core.model.user.external.responce;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

/**
 * Created by Diana Gevorgyan
 * Date: 7/14/2021
 * Time: 4:52 PM
 */
public class GetOrCreateExternalUserResponse extends AbstractResultResponseModel<GetOrCreateExternalUserResponseModel, UserErrorResponseModel> {

    public GetOrCreateExternalUserResponse() {
    }

    public GetOrCreateExternalUserResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public GetOrCreateExternalUserResponse(final GetOrCreateExternalUserResponseModel response) {
        super(response);
    }
}
