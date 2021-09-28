package com.vntana.core.model.catalog.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.catalog.error.FacebookCatalogSettingErrorResponseModel;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 11:35 AM
 */
public class DeleteFacebookCatalogSettingResultResponse extends AbstractResultResponseModel<FacebookCatalogSettingResponseModel, FacebookCatalogSettingErrorResponseModel> {

    public DeleteFacebookCatalogSettingResultResponse() {
    }

    public DeleteFacebookCatalogSettingResultResponse(final String uuid) {
        super(new FacebookCatalogSettingResponseModel(uuid));
    }

    public DeleteFacebookCatalogSettingResultResponse(final int httpStatusCode, final FacebookCatalogSettingErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}
