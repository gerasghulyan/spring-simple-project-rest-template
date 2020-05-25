package com.vntana.core.rest.resource.token;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest;
import com.vntana.core.model.token.request.CreateTokenInvitationUserRequest;
import com.vntana.core.model.token.response.TokenCreateResultResponse;
import com.vntana.core.model.token.response.TokenExpireResultResponse;
import com.vntana.core.model.token.response.TokenIsExpiredResultResponse;
import com.vntana.core.rest.facade.token.TokenServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 12:09 PM
 */
@RestController
@RequestMapping(value = "tokens", produces = "application/json")
public class TokenResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenResource.class);

    private final TokenServiceFacade tokenServiceFacade;

    public TokenResource(final TokenServiceFacade tokenServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.tokenServiceFacade = tokenServiceFacade;
    }

    @PostMapping(path = "organization-invitations")
    public ResponseEntity<TokenCreateResultResponse> createTokenInvitationOrganization(@RequestBody final CreateTokenInvitationOrganizationRequest request) {
        LOGGER.debug("Processing token resource createTokenInvitationOrganization for request - {}", request);
        final TokenCreateResultResponse resultResponse = tokenServiceFacade.createTokenInvitationOrganization(request);
        LOGGER.debug("Successfully processed token resource createTokenInvitationOrganization for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping(path = "user-invitations")
    public ResponseEntity<TokenCreateResultResponse> createTokenInvitationUser(@RequestBody final CreateTokenInvitationUserRequest request) {
        LOGGER.debug("Processing token resource createTokenInvitationUser for request - {}", request);
        final TokenCreateResultResponse resultResponse = tokenServiceFacade.createTokenInvitationUser(request);
        LOGGER.debug("Successfully processed token resource createTokenInvitationUser for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PutMapping(path = "expire")
    public ResponseEntity<TokenExpireResultResponse> expire(@RequestBody final String token) {
        LOGGER.debug("Processing token resource expire");
        final TokenExpireResultResponse resultResponse = tokenServiceFacade.expire(token);
        LOGGER.debug("Successfully processed token resource expire");
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @GetMapping(path = "check-expiration")
    public ResponseEntity<TokenIsExpiredResultResponse> isExpire(@RequestParam("token") final String token) {
        LOGGER.debug("Processing token resource isExpire");
        final TokenIsExpiredResultResponse resultResponse = tokenServiceFacade.isExpired(token);
        LOGGER.debug("Successfully processed token resource isExpire");
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }
}
