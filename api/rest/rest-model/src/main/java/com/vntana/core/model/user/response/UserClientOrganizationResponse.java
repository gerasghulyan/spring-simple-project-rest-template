package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
import com.vntana.core.model.user.response.model.GetUserClientOrganizationsGridResponseModel;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 11:42 AM
 */
public class UserClientOrganizationResponse extends AbstractResultResponseModel<GetUserClientOrganizationsGridResponseModel, ClientOrganizationErrorResponseModel> {

    protected UserClientOrganizationResponse() {
    }

    public UserClientOrganizationResponse(final GetUserClientOrganizationsGridResponseModel response) {
        super(response);
    }

    public UserClientOrganizationResponse(final int httpStatusCode, final ClientOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}