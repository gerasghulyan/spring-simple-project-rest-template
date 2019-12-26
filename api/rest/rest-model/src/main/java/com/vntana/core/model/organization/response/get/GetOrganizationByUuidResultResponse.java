package com.vntana.core.model.organization.response.get;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 3:15 PM
 */
public class GetOrganizationByUuidResultResponse extends AbstractResultResponseModel<GetOrganizationByUuidResponseModel, OrganizationErrorResponseModel> {

    public GetOrganizationByUuidResultResponse() {
        super();
    }

    public GetOrganizationByUuidResultResponse(final OrganizationErrorResponseModel error) {
        super(error);
    }

    public GetOrganizationByUuidResultResponse(final List<OrganizationErrorResponseModel> errors) {
        super(errors);
    }

    public GetOrganizationByUuidResultResponse(final GetOrganizationByUuidResponseModel response) {
        super(response);
    }
}
