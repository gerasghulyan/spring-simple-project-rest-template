package com.vntana.core.model.client.response.get;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/20
 * Time: 5:10 PM
 */
public class GetAllOrganizationsResultResponse extends AbstractResultResponseModel<GetAllOrganizationsGridResponseModel, OrganizationErrorResponseModel> {
    public GetAllOrganizationsResultResponse() {
    }

    public GetAllOrganizationsResultResponse(final int totalCount, final List<GetAllOrganizationsResponseModel> items) {
        super(new GetAllOrganizationsGridResponseModel(totalCount, items));
    }

    public GetAllOrganizationsResultResponse(final OrganizationErrorResponseModel error) {
        super(error);
    }

    public GetAllOrganizationsResultResponse(final List<OrganizationErrorResponseModel> errors) {
        super(errors);
    }
}
