package com.vntana.core.model.client.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan.
 * Date: 10/10/19
 * Time: 10:42 AM
 */
public class UpdateClientOrganizationResultResponse extends AbstractResultResponseModel<UpdateClientOrganizationResponseModel, ClientOrganizationErrorResponseModel> {
    public UpdateClientOrganizationResultResponse() {
    }

    public UpdateClientOrganizationResultResponse(final String uuid) {
        super(new UpdateClientOrganizationResponseModel(uuid));
    }

    public UpdateClientOrganizationResultResponse(final List<ClientOrganizationErrorResponseModel> errors) {
        super(errors);
    }
}
