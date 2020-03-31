package com.vntana.core.model.organization.response.get;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 2:55 PM
 */
public class GetOrganizationBySlugResultResponse extends AbstractResultResponseModel<GetOrganizationBySlugResponseModel, OrganizationErrorResponseModel> {
    public GetOrganizationBySlugResultResponse() {
    }

    public GetOrganizationBySlugResultResponse(final GetOrganizationBySlugResponseModel response) {
        super(response);
    }

    public GetOrganizationBySlugResultResponse(final int httpStatusCode, final OrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}
