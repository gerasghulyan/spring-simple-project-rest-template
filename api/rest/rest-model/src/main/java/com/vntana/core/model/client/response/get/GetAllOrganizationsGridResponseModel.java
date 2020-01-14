package com.vntana.core.model.client.response.get;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/20
 * Time: 5:11 PM
 */
public class GetAllOrganizationsGridResponseModel extends AbstractGridResponseModel<GetAllOrganizationsResponseModel> {
    public GetAllOrganizationsGridResponseModel() {
    }

    public GetAllOrganizationsGridResponseModel(final int totalCount, final List<GetAllOrganizationsResponseModel> items) {
        super(totalCount, items);
    }
}
