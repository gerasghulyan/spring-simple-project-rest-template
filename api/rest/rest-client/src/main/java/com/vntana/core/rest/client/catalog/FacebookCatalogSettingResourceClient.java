package com.vntana.core.rest.client.catalog;

import com.vntana.core.model.catalog.request.CreateFacebookCatalogSettingRequest;
import com.vntana.core.model.catalog.request.GetByCatalogIdFacebookCatalogSettingRequest;
import com.vntana.core.model.catalog.request.GetByOrganizationFacebookCatalogSettingRequest;
import com.vntana.core.model.catalog.response.DeleteFacebookCatalogSettingResultResponse;
import com.vntana.core.model.catalog.response.FacebookCatalogSettingCreateResultResponse;
import com.vntana.core.model.catalog.response.FacebookCatalogSettingResultResponse;
import com.vntana.core.model.catalog.response.GetByOrganizationFacebookCatalogSettingResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 11:46 AM
 */
@FeignClient(name = "facebookCatalogSettings", path = "facebook-catalog-settings", url = "${ms.core.url}")
public interface FacebookCatalogSettingResourceClient {

    @PostMapping
    ResponseEntity<FacebookCatalogSettingCreateResultResponse> create(@RequestBody final CreateFacebookCatalogSettingRequest request);

    @PostMapping(path = "/organization")
    ResponseEntity<GetByOrganizationFacebookCatalogSettingResultResponse> getByOrganization(@RequestBody final GetByOrganizationFacebookCatalogSettingRequest request);

    @DeleteMapping(path = "/{uuid}")
    ResponseEntity<DeleteFacebookCatalogSettingResultResponse> delete(@PathVariable("uuid") final String uuid);

    @PostMapping(path = "/catalogId")
    ResponseEntity<FacebookCatalogSettingResultResponse> getByCatalogId(@RequestBody final GetByCatalogIdFacebookCatalogSettingRequest request);
}
