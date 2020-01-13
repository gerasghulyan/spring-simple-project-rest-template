package com.vntana.core.model.client.response.get;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 2:55 PM
 */
public class GetClientOrganizationResultResponse extends AbstractResultResponseModel<GetClientOrganizationResponseModel, ClientOrganizationErrorResponseModel> {
    public GetClientOrganizationResultResponse() {
    }

    public GetClientOrganizationResultResponse(final List<ClientOrganizationErrorResponseModel> errors) {
        super(errors);
    }

    public GetClientOrganizationResultResponse(final GetClientOrganizationResponseModel response) {
        super(response);
    }
}
