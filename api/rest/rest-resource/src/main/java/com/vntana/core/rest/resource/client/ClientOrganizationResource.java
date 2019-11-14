package com.vntana.core.rest.resource.client;

import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest;
import com.vntana.core.model.client.request.CreateClientOrganizationRequest;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse;
import com.vntana.core.model.client.response.CreateClientOrganizationResultResponse;
import com.vntana.core.model.user.response.UserClientOrganizationResponse;
import com.vntana.core.rest.facade.client.ClientOrganizationServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:32 PM
 */
@RestController
@RequestMapping(value = "/clients",
        produces = "application/json"
)
public class ClientOrganizationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientOrganizationResource.class);

    private final ClientOrganizationServiceFacade clientOrganizationServiceFacade;

    public ClientOrganizationResource(final ClientOrganizationServiceFacade clientOrganizationServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.clientOrganizationServiceFacade = clientOrganizationServiceFacade;
    }

    @PostMapping(path = "/slug-availability")
    public ResponseEntity<CheckAvailableClientOrganizationSlugResultResponse> checkSlugAvailability(@RequestBody final CheckAvailableClientOrganizationSlugRequest request) {
        LOGGER.debug("Checking slug availability for request - {}", request);
        final CheckAvailableClientOrganizationSlugResultResponse resultResponse = clientOrganizationServiceFacade.checkSlugAvailability(request);
        LOGGER.debug("Successfully checked slug availability with response - {}", resultResponse);
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<CreateClientOrganizationResultResponse> create(@RequestBody final CreateClientOrganizationRequest request) {
        LOGGER.debug("Creating client organization for request - {}", request);
        final CreateClientOrganizationResultResponse resultResponse = clientOrganizationServiceFacade.create(request);
        LOGGER.debug("Successfully created client organization with response - {}", resultResponse);
        return ResponseEntity.ok(resultResponse);
    }

    @GetMapping(path = "/users/{userUuid}/organizations/{userOrganizationUuid}")
    public ResponseEntity<UserClientOrganizationResponse> getUserClientOrganizations(@PathVariable("userUuid") final String userUuid,
                                                                                     @PathVariable("userOrganizationUuid") final String userOrganizationUuid) {
        LOGGER.debug("Processing find client organization by user uuid - {} and by organization uuid - {}", userUuid, userOrganizationUuid);
        final UserClientOrganizationResponse userClientOrganizationResponse = clientOrganizationServiceFacade.getUserClientOrganizations(userUuid, userOrganizationUuid);
        LOGGER.debug("Successfully proceeded find client organizations by user uuid and by organization uuid with response - {}", userClientOrganizationResponse);
        return ResponseEntity.ok(userClientOrganizationResponse);
    }
}
