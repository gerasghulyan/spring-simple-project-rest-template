package com.vntana.core.model.organization.response.update.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 4:02 PM
 */
public class UpdateOrganizationResultResponse extends AbstractResultResponseModel<UpdateOrganizationResponseModel, OrganizationErrorResponseModel> {

    public UpdateOrganizationResultResponse() {
        super();
    }

    public UpdateOrganizationResultResponse(final String uuid) {
        super(new UpdateOrganizationResponseModel(uuid));
    }

    public UpdateOrganizationResultResponse(final int httpStatusCode, final OrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}
