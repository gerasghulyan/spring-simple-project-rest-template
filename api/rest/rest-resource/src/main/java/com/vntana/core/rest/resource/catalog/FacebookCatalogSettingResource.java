package com.vntana.core.rest.resource.catalog;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.catalog.request.CreateFacebookCatalogSettingRequest;
import com.vntana.core.model.catalog.request.GetByCatalogIdFacebookCatalogSettingRequest;
import com.vntana.core.model.catalog.request.GetByOrganizationFacebookCatalogSettingRequest;
import com.vntana.core.model.catalog.response.DeleteFacebookCatalogSettingResultResponse;
import com.vntana.core.model.catalog.response.FacebookCatalogSettingCreateResultResponse;
import com.vntana.core.model.catalog.response.FacebookCatalogSettingResultResponse;
import com.vntana.core.model.catalog.response.GetByOrganizationFacebookCatalogSettingResultResponse;
import com.vntana.core.rest.facade.catalog.FacebookCatalogSettingServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 11:48 AM
 */
@RestController
@RequestMapping(value = "/facebook-catalog-settings", produces = MediaType.APPLICATION_JSON_VALUE)
public class FacebookCatalogSettingResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookCatalogSettingResource.class);

    private final FacebookCatalogSettingServiceFacade facebookCatalogSettingServiceFacade;

    public FacebookCatalogSettingResource(final FacebookCatalogSettingServiceFacade facebookCatalogSettingServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.facebookCatalogSettingServiceFacade = facebookCatalogSettingServiceFacade;
    }

    @PostMapping
    public ResponseEntity<FacebookCatalogSettingCreateResultResponse> create(@RequestBody final CreateFacebookCatalogSettingRequest request) {
        LOGGER.debug("Creating facebook catalog setting for request - {}", request);
        final FacebookCatalogSettingCreateResultResponse resultResponse = facebookCatalogSettingServiceFacade.create(request);
        LOGGER.debug("Successfully created facebook catalog setting with response - {}", resultResponse);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping(path = "/organization")
    public ResponseEntity<GetByOrganizationFacebookCatalogSettingResultResponse> getByOrganization(@RequestBody GetByOrganizationFacebookCatalogSettingRequest request) {
        LOGGER.debug("Getting facebook catalog setting for request - {}", request);
        final GetByOrganizationFacebookCatalogSettingResultResponse response = facebookCatalogSettingServiceFacade.getByOrganization(request);
        LOGGER.debug("Successfully retrieved all facebook catalog settings with response - {}", response);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<DeleteFacebookCatalogSettingResultResponse> delete(@PathVariable("uuid") final String uuid) {
        LOGGER.debug("Deleting facebook catalog setting for uuid - {}", uuid);
        final DeleteFacebookCatalogSettingResultResponse response = facebookCatalogSettingServiceFacade.delete(uuid);
        LOGGER.debug("Successfully deleted facebook catalog setting for uuid - {}", uuid);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @PostMapping(path = "/catalogId")
    ResponseEntity<FacebookCatalogSettingResultResponse> getByCatalogId(@RequestBody final GetByCatalogIdFacebookCatalogSettingRequest request) {
        LOGGER.debug("Getting facebook catalog setting for request - {}", request);
        final FacebookCatalogSettingResultResponse response = facebookCatalogSettingServiceFacade.getByCatalogId(request);
        LOGGER.debug("Successfully done deleting facebook catalog setting with response - {}", response);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @GetMapping(path = "/{uuid}")
    ResponseEntity<FacebookCatalogSettingResultResponse> getByUuid(@PathVariable("uuid") final String uuid) {
        final FacebookCatalogSettingResultResponse response = facebookCatalogSettingServiceFacade.getByUuid(uuid);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }
}
