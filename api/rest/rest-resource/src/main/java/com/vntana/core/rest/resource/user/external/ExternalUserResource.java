package com.vntana.core.rest.resource.user.external;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.user.external.request.GetOrCreateExternalUserRequest;
import com.vntana.core.model.user.external.responce.GetOrCreateExternalUserResponse;
import com.vntana.core.rest.facade.user.external.ExternalUserServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Diana Gevorgyan
 * Date: 7/16/2021
 * Time: 2:53 PM
 */
@RestController
@RequestMapping(value = "/external-user",
        produces = "application/json"
)
public class ExternalUserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalUserResource.class);

    private final ExternalUserServiceFacade externalUserServiceFacade;

    public ExternalUserResource(final ExternalUserServiceFacade externalUserServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.externalUserServiceFacade = externalUserServiceFacade;
    }

    @PostMapping(path = "/get-external-user")
    ResponseEntity<GetOrCreateExternalUserResponse> getOrCreateExternalUser(@RequestBody final GetOrCreateExternalUserRequest request) {
        LOGGER.debug("Processing external-user resource getExternalUser for request - {}", request);
        final GetOrCreateExternalUserResponse response = externalUserServiceFacade.getOrCreateExternalUser(request);
        LOGGER.debug("Successfully processing external-user resource getExternalUser with response - {}", response);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }
}
