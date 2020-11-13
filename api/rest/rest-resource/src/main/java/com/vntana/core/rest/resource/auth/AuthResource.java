package com.vntana.core.rest.resource.auth;

import com.vntana.core.model.security.request.FindUserByUuidAndClientOrganizationRequest;
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest;
import com.vntana.core.model.security.response.SecureFindUserByEmailResponse;
import com.vntana.core.model.security.response.SecureFindUserByUuidAndClientOrganizationResponse;
import com.vntana.core.model.security.response.SecureFindUserByUuidAndOrganizationResponse;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.rest.facade.auth.AuthFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 10:41 AM
 */
@RestController
@RequestMapping(value = "/auth",
        produces = "application/json"
)
public class AuthResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthResource.class);

    private final AuthFacade authServiceFacade;

    public AuthResource(final AuthFacade authServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.authServiceFacade = authServiceFacade;
    }

    @PostMapping(path = "/by-email")
    public ResponseEntity<SecureFindUserByEmailResponse> findByEmail(@RequestBody final FindUserByEmailRequest request) {
        LOGGER.debug("Processing find user by request - {}", request);
        final SecureFindUserByEmailResponse response = authServiceFacade.findByEmail(request);
        LOGGER.debug("Successfully proceeded find user by request with response - {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/by-user-and-organization")
    public ResponseEntity<SecureFindUserByUuidAndOrganizationResponse> findByUserAndOrganization(@RequestBody final FindUserByUuidAndOrganizationRequest request) {
        LOGGER.debug("Processing find user by request - {}", request);
        final SecureFindUserByUuidAndOrganizationResponse response = authServiceFacade.findByUserAndOrganization(request);
        LOGGER.debug("Successfully proceeded find user by request with response - {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/by-user-and-client-organization")
    public ResponseEntity<SecureFindUserByUuidAndClientOrganizationResponse> findByUserAndClientOrganization(@RequestBody final FindUserByUuidAndClientOrganizationRequest request) {
        LOGGER.debug("Processing find user by request - {}", request);
        final SecureFindUserByUuidAndClientOrganizationResponse response = authServiceFacade.findByUserAndClientOrganization(request);
        LOGGER.debug("Successfully proceeded find user by request with response - {}", response);
        return ResponseEntity.ok(response);
    }
}
