package com.vntana.core.rest.resource.auth;

import com.vntana.core.model.security.request.FindUserByPersonalAccessTokenRequest;
import com.vntana.core.model.security.request.FindUserByUuidAndClientOrganizationRequest;
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest;
import com.vntana.core.model.security.response.*;
import com.vntana.core.model.security.request.CreatePersonalAccessTokenRequest;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.model.user.request.RegeneratePersonalAccessTokenRequest;
import com.vntana.core.rest.facade.auth.AuthFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/by-personal-access-token")
    ResponseEntity<SecureFindUserByPersonalAccessTokenResponse> findByPersonalAccessToken(@RequestBody final FindUserByPersonalAccessTokenRequest request) {
        LOGGER.debug("Processing find user by personal access token method for request - {}", request);
        final SecureFindUserByPersonalAccessTokenResponse response = authServiceFacade.findByPersonalAccessToken(request);
        LOGGER.debug("Processing find user by personal access token method with response - {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/create/personal-access-token")
    public ResponseEntity<PersonalAccessTokenResponse> createPersonalAccessToken(@RequestBody final CreatePersonalAccessTokenRequest request) {
        LOGGER.debug("Processing user resource createPersonalAccessToken method for request - {}", request);
        final PersonalAccessTokenResponse response = authServiceFacade.createPersonalAccessToken(request);
        LOGGER.debug("Successfully processed user resource createPersonalAccessToken method with response - {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/expire/personal-access-token/{userUuid}")
    public ResponseEntity<PersonalAccessTokenExpireResponse> expirePersonalAccessTokenByUserUuid(@PathVariable("userUuid") final String userUuid) {
        LOGGER.debug("Processing user resource expirePersonalAccessTokenByUserUuid method for user uuid - {}", userUuid);
        final PersonalAccessTokenExpireResponse response = authServiceFacade.expirePersonalAccessTokenByUserUuid(userUuid);
        LOGGER.debug("Successfully processed user resource expirePersonalAccessTokenByUserUuid method for user uuid - {}", userUuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/personal-access-token/{userUuid}")
    public ResponseEntity<PersonalAccessTokenResponse> findPersonalAccessTokenByUserUuid(@PathVariable("userUuid") final String userUuid) {
        LOGGER.debug("Processing user resource findPersonalAccessTokenByUserUuid method for user uuid - {}", userUuid);
        final PersonalAccessTokenResponse response = authServiceFacade.findPersonalAccessTokenByUserUuid(userUuid);
        LOGGER.debug("Successfully processed user resource findPersonalAccessTokenByUserUuid method for user uuid - {}", userUuid);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "personal-access-token/regenerate")
    public ResponseEntity<PersonalAccessTokenResponse> regeneratePersonalAccessToken(@RequestBody final RegeneratePersonalAccessTokenRequest request) {
        LOGGER.debug("Processing user resource regeneratePersonalAccessToken method for request - {}", request);
        final PersonalAccessTokenResponse response = authServiceFacade.regeneratePersonalAccessToken(request);
        LOGGER.debug("Successfully processed user resource createPersonalAccessToken method with response - {}", response);
        return ResponseEntity.ok(response);
    }
}
