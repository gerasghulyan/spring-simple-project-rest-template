package com.vntana.core.rest.resource.token.auth;

import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistRequest;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithClientOrganizationRequest;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithOrganizationRequest;
import com.vntana.core.model.token.auth.request.TokenAuthenticationRequest;
import com.vntana.core.model.token.auth.response.*;
import com.vntana.core.rest.facade.token.auth.TokenAuthenticationServiceFacade;
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
public class TokenAuthenticationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationResource.class);

    private final TokenAuthenticationServiceFacade tokenAuthenticationServiceFacade;

    public TokenAuthenticationResource(final TokenAuthenticationServiceFacade tokenAuthenticationServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.tokenAuthenticationServiceFacade = tokenAuthenticationServiceFacade;
    }

    @PostMapping("persist")
    public ResponseEntity<PersistTokenAuthenticationResultResponse> persist(@RequestBody final TokenAuthenticationPersistRequest request) {
        LOGGER.debug("Processing auth-token resource persist for user having uuid - {}", request.getUserUuid());
        final PersistTokenAuthenticationResultResponse resultResponse = tokenAuthenticationServiceFacade.persist(request);
        LOGGER.debug("Successfully processed auth-token resource persist for user having uuid - {}", request.getUserUuid());
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping("persist-with-organization")
    public ResponseEntity<PersistTokenAuthenticationResultResponse> persistWithOrganization(@RequestBody final TokenAuthenticationPersistWithOrganizationRequest request) {
        LOGGER.debug("Processing auth-token resource persist for user having uuid - {}", request.getUserUuid());
        final PersistTokenAuthenticationResultResponse resultResponse = tokenAuthenticationServiceFacade.persistWithOrganization(request);
        LOGGER.debug("Successfully processed auth-token resource persist for user having uuid - {}", request.getUserUuid());
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping("persist-with-client-organization")
    public ResponseEntity<PersistTokenAuthenticationResultResponse> persistWithClientOrganization(@RequestBody final TokenAuthenticationPersistWithClientOrganizationRequest request) {
        LOGGER.debug("Processing auth-token resource persist for user having uuid - {}", request.getUserUuid());
        final PersistTokenAuthenticationResultResponse resultResponse = tokenAuthenticationServiceFacade.persistWithClientOrganization(request);
        LOGGER.debug("Successfully processed auth-token resource persist for user having uuid - {}", request.getUserUuid());
        return ResponseEntity.ok(resultResponse);
    }

    @GetMapping("expiration/{token}")
    public ResponseEntity<IsExpiredTokenAuthenticationResultResponse> isExpired(@PathVariable("token") final String token) {
        final IsExpiredTokenAuthenticationResultResponse resultResponse = tokenAuthenticationServiceFacade.isExpired(token);
        return ResponseEntity.ok(resultResponse);
    }

    @DeleteMapping("expiration/users/{userUuid}")
    public ResponseEntity<ExpireTokenAuthenticationByUserResultResponse> expireByUser(@PathVariable("userUuid") final String userUuid) {
        LOGGER.debug("Expiring all tokens of user having uuid - {}", userUuid);
        final ExpireTokenAuthenticationByUserResultResponse resultResponse = tokenAuthenticationServiceFacade.expireByUser(userUuid);
        LOGGER.debug("Successfully expired all tokens of user having uuid - {}", userUuid);
        return ResponseEntity.ok(resultResponse);
    }

    @DeleteMapping("expiration/{token}")
    public ResponseEntity<ExpireTokenAuthenticationResultResponse> expire(@PathVariable("token") final String token) {
        final ExpireTokenAuthenticationResultResponse resultResponse = tokenAuthenticationServiceFacade.expire(token);
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping("tokens")
    public ResponseEntity<FindTokenAuthenticationByTokenResponse> findByToken(@RequestBody final TokenAuthenticationRequest request) {
        LOGGER.debug("Retrieving auth-token by token");
        final FindTokenAuthenticationByTokenResponse resultResponse = tokenAuthenticationServiceFacade.findByToken(request.getToken());
        LOGGER.debug("Successfully retrieved auth-token by token");
        return ResponseEntity.ok(resultResponse);
    }
}
