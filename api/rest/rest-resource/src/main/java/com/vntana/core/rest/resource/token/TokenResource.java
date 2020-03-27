package com.vntana.core.rest.resource.token;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest;
import com.vntana.core.model.token.response.TokenCreateResultResponse;
import com.vntana.core.rest.facade.token.TokenServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
