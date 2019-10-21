package com.vntana.core.rest.resource.organization;

import com.vntana.core.model.organization.request.CheckAvailableOrganizationSlugRequest;
import com.vntana.core.model.organization.request.CreateOrganizationRequest;
import com.vntana.core.model.organization.response.CheckAvailableOrganizationSlugResultResponse;
import com.vntana.core.model.organization.response.CreateOrganizationResultResponse;
import com.vntana.core.rest.facade.organization.OrganizationServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:32 PM
 */
@RestController
@RequestMapping(value = "/organizations",
        consumes = "application/json",
        produces = "application/json"
)
public class OrganizationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationResource.class);

    private final OrganizationServiceFacade organizationServiceFacade;

    public OrganizationResource(final OrganizationServiceFacade organizationServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationServiceFacade = organizationServiceFacade;
    }

    @PostMapping(path = "/slug-availability")
    public ResponseEntity<CheckAvailableOrganizationSlugResultResponse> checkSlugAvailability(@RequestBody final CheckAvailableOrganizationSlugRequest request) {
        LOGGER.debug("Checking slug availability for request - {}", request);
        final CheckAvailableOrganizationSlugResultResponse resultResponse = organizationServiceFacade.checkSlugAvailability(request);
        LOGGER.debug("Successfully checked slug availability with response - {}", resultResponse);
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<CreateOrganizationResultResponse> create(@RequestBody final CreateOrganizationRequest request) {
        LOGGER.debug("Creating organization for request - {}", request);
        final CreateOrganizationResultResponse resultResponse = organizationServiceFacade.create(request);
        LOGGER.debug("Successfully created organization with response - {}", resultResponse);
        return ResponseEntity.ok(resultResponse);
    }
}
