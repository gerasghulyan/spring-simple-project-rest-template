package com.vntana.core.model.user.response.get.model;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 4:54 PM
 */
public class GetUsersByOrganizationGridResponseModel extends AbstractGridResponseModel<GetUsersByOrganizationResponseModel> {

    public GetUsersByOrganizationGridResponseModel() {
        super();
    }

    public GetUsersByOrganizationGridResponseModel(final int totalCount, final List<GetUsersByOrganizationResponseModel> items) {
        super(totalCount, items);
    }

    public GetUsersByOrganizationGridResponseModel(final List<GetUsersByOrganizationResponseModel> items) {
        super(items.size(), items);
    }
}
