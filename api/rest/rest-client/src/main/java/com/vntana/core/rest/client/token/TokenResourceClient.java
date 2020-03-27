package com.vntana.core.rest.client.token;

import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest;
import com.vntana.core.model.token.response.TokenCreateResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 12:15 PM
 */
@FeignClient(name = "coreTokens", primary = false, path = "tokens", url = "${ms.core.url}")
public interface TokenResourceClient {

    @PostMapping(path = "organization-invitations")
    ResponseEntity<TokenCreateResultResponse> createTokenInvitationOrganization(@RequestBody final CreateTokenInvitationOrganizationRequest request);
}
