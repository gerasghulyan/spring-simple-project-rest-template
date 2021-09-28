package com.vntana.core.model.catalog.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.catalog.error.FacebookCatalogSettingErrorResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 8:45 AM
 */
public class GetByOrganizationFacebookCatalogSettingResultResponse extends AbstractResultResponseModel<GetByOrganizationFacebookCatalogSettingGridResponseModel, FacebookCatalogSettingErrorResponseModel> {

    public GetByOrganizationFacebookCatalogSettingResultResponse() {
    }

    public GetByOrganizationFacebookCatalogSettingResultResponse(final int totalCount, final List<GetByOrganizationFacebookCatalogSettingResponseModel> items) {
        super(new GetByOrganizationFacebookCatalogSettingGridResponseModel(totalCount, items));
    }

    public GetByOrganizationFacebookCatalogSettingResultResponse(final int httpStatusCode, final FacebookCatalogSettingErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}
