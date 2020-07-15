package com.vntana.core.rest.resource.token.auth;

import com.vntana.core.model.token.auth.request.AuthTokenPersistRequest;
import com.vntana.core.model.token.auth.request.AuthTokenPersistWithOrganizationRequest;
import com.vntana.core.model.token.auth.response.*;
import com.vntana.core.rest.facade.token.auth.AuthTokenServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:58 AM
 */
@RestController
@RequestMapping(value = "/auth-tokens", produces = "application/json")
public class AuthTokenResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenResource.class);

    private final AuthTokenServiceFacade authTokenServiceFacade;

    public AuthTokenResource(final AuthTokenServiceFacade authTokenServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.authTokenServiceFacade = authTokenServiceFacade;
    }

    @PostMapping("persist")
    public ResponseEntity<AuthTokenPersistResultResponse> persist(@RequestBody final AuthTokenPersistRequest request) {
        LOGGER.debug("Processing auth-token resource persist for user having uuid - {}", request.getUserUuid());
        final AuthTokenPersistResultResponse resultResponse = authTokenServiceFacade.persist(request);
        LOGGER.debug("Successfully processed auth-token resource persist for user having uuid - {}", request.getUserUuid());
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping("persist-with-organization")
    public ResponseEntity<AuthTokenPersistResultResponse> persistWithOrganization(@RequestBody final AuthTokenPersistWithOrganizationRequest request) {
        LOGGER.debug("Processing auth-token resource persist for user having uuid - {}", request.getUserUuid());
        final AuthTokenPersistResultResponse resultResponse = authTokenServiceFacade.persistWithOrganization(request);
        LOGGER.debug("Successfully processed auth-token resource persist for user having uuid - {}", request.getUserUuid());
        return ResponseEntity.ok(resultResponse);
    }

    @GetMapping("expiration/{token}")
    public ResponseEntity<AuthTokenIsExpiredResultResponse> isExpired(@PathVariable("token") final String token) {
        final AuthTokenIsExpiredResultResponse resultResponse = authTokenServiceFacade.isExpired(token);
        return ResponseEntity.ok(resultResponse);
    }

    @DeleteMapping("expiration/users/{userUuid}")
    public ResponseEntity<AuthTokenExpireByUserResultResponse> expireByUser(@PathVariable("userUuid") final String userUuid) {
        LOGGER.debug("Expiring all tokens of user having uuid - {}", userUuid);
        final AuthTokenExpireByUserResultResponse resultResponse = authTokenServiceFacade.expireByUser(userUuid);
        LOGGER.debug("Successfully expired all tokens of user having uuid - {}", userUuid);
        return ResponseEntity.ok(resultResponse);
    }

    @DeleteMapping("expiration/{token}")
    public ResponseEntity<AuthTokenExpireResultResponse> expire(@PathVariable("token") final String token) {
        final AuthTokenExpireResultResponse resultResponse = authTokenServiceFacade.expire(token);
        return ResponseEntity.ok(resultResponse);
    }

    @GetMapping("tokens/{token}")
    public ResponseEntity<AuthTokenFindByTokenResponse> findByToken(@PathVariable("token") final String token) {
        LOGGER.debug("Retrieving auth-token by token");
        final AuthTokenFindByTokenResponse resultResponse = authTokenServiceFacade.findByToken(token);
        LOGGER.debug("Successfully retrieved auth-token by token");
        return ResponseEntity.ok(resultResponse);
    }
}
