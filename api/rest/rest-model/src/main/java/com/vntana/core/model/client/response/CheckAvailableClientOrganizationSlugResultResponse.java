package com.vntana.core.model.client.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 2:55 PM
 */
public class CheckAvailableClientOrganizationSlugResultResponse extends AbstractResultResponseModel<CheckAvailableClientOrganizationSlugResponseModel, ClientOrganizationErrorResponseModel> {
    public CheckAvailableClientOrganizationSlugResultResponse() {
        super();
    }

    public CheckAvailableClientOrganizationSlugResultResponse(final int httpStatusCode, final ClientOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public CheckAvailableClientOrganizationSlugResultResponse(final CheckAvailableClientOrganizationSlugResponseModel response) {
        super(response);
    }
}
