package com.vntana.core.model.user.response.get;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.get.model.GetUsersByUuidsAndOrganizationUuidGridResponseModel;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 29.09.2020
 * Time: 14:42
 */
public class GetUsersByUuidsAndOrganizationUuidResponse extends AbstractResultResponseModel<GetUsersByUuidsAndOrganizationUuidGridResponseModel, UserErrorResponseModel> {

    public GetUsersByUuidsAndOrganizationUuidResponse() {
        super();
    }

    public GetUsersByUuidsAndOrganizationUuidResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public GetUsersByUuidsAndOrganizationUuidResponse(final int httpStatusCode, final List<UserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public GetUsersByUuidsAndOrganizationUuidResponse(final GetUsersByUuidsAndOrganizationUuidGridResponseModel response) {
        super(response);
    }
}