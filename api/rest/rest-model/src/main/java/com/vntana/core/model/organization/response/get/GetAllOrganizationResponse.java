package com.vntana.core.model.organization.response.get;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;

/**
 * Created by Geras Ghulyan
 * Date: 04.11.19
 * Time: 16:30
 */
public class GetAllOrganizationResponse extends AbstractResultResponseModel<GetAllOrganizationsGridResponseModel, OrganizationErrorResponseModel> {

    public GetAllOrganizationResponse() {
        super();
    }

    public GetAllOrganizationResponse(final GetAllOrganizationsGridResponseModel response) {
        super(response);
    }

    public GetAllOrganizationResponse(final int httpStatusCode, final OrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}