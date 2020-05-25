package com.vntana.core.model.user.response.get;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.get.model.GetUsersByRoleAndOrganizationUuidGridResponseModel;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 4/7/2020
 * Time: 1:28 PM
 */
public class GetUsersByRoleAndOrganizationUuidResponse extends AbstractResultResponseModel<GetUsersByRoleAndOrganizationUuidGridResponseModel, UserErrorResponseModel> {

    public GetUsersByRoleAndOrganizationUuidResponse() {
    }

    public GetUsersByRoleAndOrganizationUuidResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public GetUsersByRoleAndOrganizationUuidResponse(final int httpStatusCode, final List<UserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public GetUsersByRoleAndOrganizationUuidResponse(final GetUsersByRoleAndOrganizationUuidGridResponseModel response) {
        super(response);
    }
}