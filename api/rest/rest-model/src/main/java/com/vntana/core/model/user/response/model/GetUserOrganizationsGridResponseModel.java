package com.vntana.core.model.user.response.model;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan
 * Date: 04.11.19
 * Time: 16:33
 */
public class GetUserOrganizationsGridResponseModel extends AbstractGridResponseModel<GetUserOrganizationsResponseModel> {

    public GetUserOrganizationsGridResponseModel() {
    }

    public GetUserOrganizationsGridResponseModel(final int totalCount, final List<GetUserOrganizationsResponseModel> items) {
        super(totalCount, items);
    }
}
