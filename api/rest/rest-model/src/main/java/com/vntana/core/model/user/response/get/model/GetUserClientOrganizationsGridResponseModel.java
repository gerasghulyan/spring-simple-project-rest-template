package com.vntana.core.model.user.response.get.model;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 11:44 AM
 */
public class GetUserClientOrganizationsGridResponseModel extends AbstractGridResponseModel<GetUserClientOrganizationsResponseModel> {
    public GetUserClientOrganizationsGridResponseModel() {
    }

    public GetUserClientOrganizationsGridResponseModel(final int totalCount, final List<GetUserClientOrganizationsResponseModel> items) {
        super(totalCount, items);
    }
}
