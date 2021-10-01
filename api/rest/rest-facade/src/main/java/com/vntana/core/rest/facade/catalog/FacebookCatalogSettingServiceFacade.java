package com.vntana.core.rest.facade.catalog;

import com.vntana.core.model.catalog.request.CreateFacebookCatalogSettingRequest;
import com.vntana.core.model.catalog.request.GetByCatalogIdFacebookCatalogSettingRequest;
import com.vntana.core.model.catalog.request.GetByOrganizationFacebookCatalogSettingRequest;
import com.vntana.core.model.catalog.response.DeleteFacebookCatalogSettingResultResponse;
import com.vntana.core.model.catalog.response.FacebookCatalogSettingCreateResultResponse;
import com.vntana.core.model.catalog.response.FacebookCatalogSettingResultResponse;
import com.vntana.core.model.catalog.response.GetByOrganizationFacebookCatalogSettingResultResponse;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 8:39 AM
 */
public interface FacebookCatalogSettingServiceFacade {

    FacebookCatalogSettingCreateResultResponse create(final CreateFacebookCatalogSettingRequest request);

    GetByOrganizationFacebookCatalogSettingResultResponse getByOrganization(final GetByOrganizationFacebookCatalogSettingRequest request);

    DeleteFacebookCatalogSettingResultResponse delete(final String uuid);

    FacebookCatalogSettingResultResponse getByCatalogId(final GetByCatalogIdFacebookCatalogSettingRequest request);

    FacebookCatalogSettingResultResponse getByUuid(final String uuid);

}
