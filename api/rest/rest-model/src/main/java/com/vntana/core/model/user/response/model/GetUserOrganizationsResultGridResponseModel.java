package com.vntana.core.model.user.response.model;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan
 * Date: 04.11.19
 * Time: 16:33
 */
public class GetUserOrganizationsResultGridResponseModel extends AbstractGridResponseModel<GetUserOrganizationsResultResponseModel> {

    public GetUserOrganizationsResultGridResponseModel() {
    }

    public GetUserOrganizationsResultGridResponseModel(final int totalCount, final List<GetUserOrganizationsResultResponseModel> items) {
        super(totalCount, items);
    }
}
