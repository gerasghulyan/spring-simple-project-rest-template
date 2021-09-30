package com.vntana.core.model.catalog.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.catalog.error.FacebookCatalogSettingErrorResponseModel;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/30/2021
 * Time: 3:26 PM
 */
public class FacebookCatalogSettingResultResponse extends AbstractResultResponseModel<FacebookCatalogSettingResponseModel, FacebookCatalogSettingErrorResponseModel> {

    public FacebookCatalogSettingResultResponse() {
    }

    public FacebookCatalogSettingResultResponse(
            final String uuid,
            final String catalogId,
            final String systemUserToken,
            final String name) {
        super(new FacebookCatalogSettingResponseModel(uuid, catalogId, systemUserToken, name));
    }

    public FacebookCatalogSettingResultResponse(final int httpStatusCode, final FacebookCatalogSettingErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}
