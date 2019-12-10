package com.vntana.core.model.organization.response.get;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 2:55 PM
 */
public class GetOrganizationResultResponse extends AbstractResultResponseModel<GetOrganizationResponseModel, OrganizationErrorResponseModel> {
    public GetOrganizationResultResponse() {
    }

    public GetOrganizationResultResponse(final List<OrganizationErrorResponseModel> errors) {
        super(errors);
    }

    public GetOrganizationResultResponse(final GetOrganizationResponseModel response) {
        super(response);
    }

}
