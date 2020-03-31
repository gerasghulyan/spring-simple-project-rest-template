package com.vntana.core.model.client.response.get;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 2:55 PM
 */
public class GetClientOrganizationBySlugResultResponse extends AbstractResultResponseModel<GetClientOrganizationResponseModel, ClientOrganizationErrorResponseModel> {
    public GetClientOrganizationBySlugResultResponse() {
    }

    public GetClientOrganizationBySlugResultResponse(final int httpStatusCode, final ClientOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public GetClientOrganizationBySlugResultResponse(final GetClientOrganizationResponseModel response) {
        super(response);
    }
}
