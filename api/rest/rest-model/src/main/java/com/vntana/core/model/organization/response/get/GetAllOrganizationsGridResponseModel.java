package com.vntana.core.model.organization.response.get;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan
 * Date: 04.11.19
 * Time: 16:33
 */
public class GetAllOrganizationsGridResponseModel extends AbstractGridResponseModel<GetAllOrganizationResponseModel> {

    public GetAllOrganizationsGridResponseModel() {
        super();
    }

    public GetAllOrganizationsGridResponseModel(final int totalCount, final List<GetAllOrganizationResponseModel> items) {
        super(totalCount, items);
    }
}
