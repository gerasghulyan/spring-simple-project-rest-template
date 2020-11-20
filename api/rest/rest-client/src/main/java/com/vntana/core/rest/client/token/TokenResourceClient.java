package com.vntana.core.rest.client.token;

import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest;
import com.vntana.core.model.token.request.CreateTokenInvitationUserToOrganizationRequest;
import com.vntana.core.model.token.request.CreateTokenUserInvitationToClientRequest;
import com.vntana.core.model.token.response.TokenBulkCreateResultResponse;
import com.vntana.core.model.token.response.TokenCreateResultResponse;
import com.vntana.core.model.token.response.TokenExpireResultResponse;
import com.vntana.core.model.token.response.TokenIsExpiredResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 12:15 PM
 */
@FeignClient(name = "coreTokens", primary = false, path = "tokens", url = "${ms.core.url}")
public interface TokenResourceClient {

    @PostMapping(path = "organization-invitations")
    ResponseEntity<TokenCreateResultResponse> createTokenInvitationOrganization(@RequestBody final CreateTokenInvitationOrganizationRequest request);

    @PostMapping(path = "user-invitations/organization")
    ResponseEntity<TokenCreateResultResponse> createTokenInvitationUserToOrganization(@RequestBody final CreateTokenInvitationUserToOrganizationRequest request);

    @PostMapping(path = "user-invitations/organization-client")
    ResponseEntity<TokenBulkCreateResultResponse> createTokenInvitationUserToClient(@RequestBody final CreateTokenUserInvitationToClientRequest request);

    @GetMapping(path = "check-expiration")
    ResponseEntity<TokenIsExpiredResultResponse> isExpire(@RequestParam("token") final String token);

    @PutMapping(path = "expire")
    ResponseEntity<TokenExpireResultResponse> expire(@RequestBody final String token);
}
