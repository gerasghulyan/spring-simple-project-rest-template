package com.vntana.core.model.organization.response.update.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 4:02 PM
 */
public class UpdateOrganizationResultResponse extends AbstractResultResponseModel<UpdateOrganizationResponseModel, OrganizationErrorResponseModel> {

    public UpdateOrganizationResultResponse() {
        super();
    }

    public UpdateOrganizationResultResponse(final OrganizationErrorResponseModel error) {
        super(error);
    }

    public UpdateOrganizationResultResponse(final List<OrganizationErrorResponseModel> errors) {
        super(errors);
    }

    public UpdateOrganizationResultResponse(final String uuid) {
        super(new UpdateOrganizationResponseModel(uuid));
    }
}
