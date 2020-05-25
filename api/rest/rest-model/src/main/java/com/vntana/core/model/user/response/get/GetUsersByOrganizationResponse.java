package com.vntana.core.model.user.response.get;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.get.model.GetUsersByOrganizationGridResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 4:44 PM
 */
public class GetUsersByOrganizationResponse extends AbstractResultResponseModel<GetUsersByOrganizationGridResponseModel, UserErrorResponseModel> {

    public GetUsersByOrganizationResponse() {
        super();
    }

    public GetUsersByOrganizationResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public GetUsersByOrganizationResponse(final int httpStatusCode, final List<UserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public GetUsersByOrganizationResponse(final GetUsersByOrganizationGridResponseModel response) {
        super(response);
    }
}
