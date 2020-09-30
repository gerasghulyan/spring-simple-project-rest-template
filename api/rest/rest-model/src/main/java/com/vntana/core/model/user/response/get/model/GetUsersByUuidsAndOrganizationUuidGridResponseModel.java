package com.vntana.core.model.user.response.get.model;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;
import com.vntana.core.model.user.response.model.GetUserByUuidsAndOrganizationUuidResponseModel;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 29.09.2020
 * Time: 15:34
 */
public class GetUsersByUuidsAndOrganizationUuidGridResponseModel extends AbstractGridResponseModel<GetUserByUuidsAndOrganizationUuidResponseModel> {

    public GetUsersByUuidsAndOrganizationUuidGridResponseModel() {
        super();
    }

    public GetUsersByUuidsAndOrganizationUuidGridResponseModel(final int totalCount, final List<GetUserByUuidsAndOrganizationUuidResponseModel> items) {
        super(totalCount, items);
    }

    public GetUsersByUuidsAndOrganizationUuidGridResponseModel(final List<GetUserByUuidsAndOrganizationUuidResponseModel> items) {
        super(items.size(), items);
    }
}
