package com.vntana.core.model.catalog.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.catalog.error.FacebookCatalogSettingErrorResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 8:41 AM
 */
public class FacebookCatalogSettingCreateResultResponse extends AbstractResultResponseModel<CreateFacebookCatalogSettingResponseModel, FacebookCatalogSettingErrorResponseModel> {

    public FacebookCatalogSettingCreateResultResponse() {
    }

    public FacebookCatalogSettingCreateResultResponse(final List<String> uuids) {
        super(new CreateFacebookCatalogSettingResponseModel(uuids));
    }

    public FacebookCatalogSettingCreateResultResponse(final int httpStatusCode, final FacebookCatalogSettingErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}

