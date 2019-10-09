package com.vntana.core.model.client.response;

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
import com.vntana.core.model.commons.response.result.AbstractResultResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 2:55 PM
 */
public class CheckAvailableClientOrganizationSlugResultResponse extends AbstractResultResponseModel<CheckAvailableClientOrganizationSlugResponseModel, ClientOrganizationErrorResponseModel> {
    public CheckAvailableClientOrganizationSlugResultResponse() {
    }

    public CheckAvailableClientOrganizationSlugResultResponse(final boolean available, final String suggested) {
        super(new CheckAvailableClientOrganizationSlugResponseModel(available, suggested));
    }

    public CheckAvailableClientOrganizationSlugResultResponse(final List<ClientOrganizationErrorResponseModel> errors) {
        super(errors);
    }
}
