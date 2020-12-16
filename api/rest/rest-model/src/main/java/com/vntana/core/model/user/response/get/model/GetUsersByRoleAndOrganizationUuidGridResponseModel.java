package com.vntana.core.model.user.response.get.model;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 4/7/2020
 * Time: 1:28 PM
 */
public class GetUsersByRoleAndOrganizationUuidGridResponseModel extends AbstractGridResponseModel<GetUsersByRoleAndOrganizationUuidResponseModel> {

    public GetUsersByRoleAndOrganizationUuidGridResponseModel() {
        super();
    }

    public GetUsersByRoleAndOrganizationUuidGridResponseModel(final int totalCount, final List<GetUsersByRoleAndOrganizationUuidResponseModel> items) {
        super(totalCount, items);
    }
}