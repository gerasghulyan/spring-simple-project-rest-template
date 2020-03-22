package com.vntana.core.model.client.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 10:42 AM
 */
public class CreateClientOrganizationResultResponse extends AbstractResultResponseModel<CreateClientOrganizationResponseModel, ClientOrganizationErrorResponseModel> {
    public CreateClientOrganizationResultResponse() {
    }

    public CreateClientOrganizationResultResponse(final String uuid) {
        super(new CreateClientOrganizationResponseModel(uuid));
    }

    public CreateClientOrganizationResultResponse(final int httpStatusCode, final ClientOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}
