package com.vntana.core.model.catalog.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.catalog.error.FacebookCatalogSettingErrorResponseModel;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 8:41 AM
 */
public class CreateFacebookCatalogSettingResultResponse extends AbstractResultResponseModel<FacebookCatalogSettingResponseModel, FacebookCatalogSettingErrorResponseModel> {

    public CreateFacebookCatalogSettingResultResponse() {
    }

    public CreateFacebookCatalogSettingResultResponse(final String uuid) {
        super(new FacebookCatalogSettingResponseModel(uuid));
    }

    public CreateFacebookCatalogSettingResultResponse(final int httpStatusCode, final FacebookCatalogSettingErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}
