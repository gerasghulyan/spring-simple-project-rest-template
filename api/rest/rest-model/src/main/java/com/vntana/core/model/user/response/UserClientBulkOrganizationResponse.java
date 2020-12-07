package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
import com.vntana.core.model.user.response.get.model.GetUserClientBulkOrganizationsGridResponseModel;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 11:42 AM
 */
public class UserClientBulkOrganizationResponse extends AbstractResultResponseModel<GetUserClientBulkOrganizationsGridResponseModel, ClientOrganizationErrorResponseModel> {

    protected UserClientBulkOrganizationResponse() {
    }

    public UserClientBulkOrganizationResponse(final GetUserClientBulkOrganizationsGridResponseModel response) {
        super(response);
    }

    public UserClientBulkOrganizationResponse(final GetUserClientBulkOrganizationsGridResponseModel response, final int httpStatusCode) {
        super(response, httpStatusCode);
    }

    public UserClientBulkOrganizationResponse(final int httpStatusCode, final ClientOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}