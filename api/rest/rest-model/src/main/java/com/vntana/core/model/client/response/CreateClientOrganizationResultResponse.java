package com.vntana.core.model.client.response;

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
import com.vntana.core.model.commons.response.result.AbstractResultResponseModel;

import java.util.List;

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

    public CreateClientOrganizationResultResponse(final List<ClientOrganizationErrorResponseModel> errors) {
        super(errors);
    }
}
