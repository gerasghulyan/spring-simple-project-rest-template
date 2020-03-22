package com.vntana.core.model.client.response.get;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 2:55 PM
 */
public class GetClientOrganizationResultResponse extends AbstractResultResponseModel<GetClientOrganizationResponseModel, ClientOrganizationErrorResponseModel> {
    public GetClientOrganizationResultResponse() {
    }

    public GetClientOrganizationResultResponse(final GetClientOrganizationResponseModel response) {
        super(response);
    }

    public GetClientOrganizationResultResponse(final int httpStatusCode, final ClientOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}
