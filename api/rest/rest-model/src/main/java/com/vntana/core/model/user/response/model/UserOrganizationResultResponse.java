package com.vntana.core.model.user.response.model;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan
 * Date: 04.11.19
 * Time: 16:30
 */
public class UserOrganizationResultResponse extends AbstractGridResponseModel<GetUserOrganizationsResultResponseModel> {

    public UserOrganizationResultResponse() {
    }

    public UserOrganizationResultResponse(final int totalCount, final List<GetUserOrganizationsResultResponseModel> items) {
        super(totalCount, items);
    }
}