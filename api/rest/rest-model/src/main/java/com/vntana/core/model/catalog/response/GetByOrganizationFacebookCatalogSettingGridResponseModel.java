package com.vntana.core.model.catalog.response;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 9:05 AM
 */
public class GetByOrganizationFacebookCatalogSettingGridResponseModel extends AbstractGridResponseModel<GetByOrganizationFacebookCatalogSettingResponseModel> {

    public GetByOrganizationFacebookCatalogSettingGridResponseModel() {
        super();
    }

    public GetByOrganizationFacebookCatalogSettingGridResponseModel(final int totalCount, final List<GetByOrganizationFacebookCatalogSettingResponseModel> items) {
        super(totalCount, items);
    }
}
