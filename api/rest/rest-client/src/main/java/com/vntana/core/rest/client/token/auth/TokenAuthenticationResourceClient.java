package com.vntana.core.rest.client.token.auth;

import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistRequest;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithClientOrganizationRequest;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithOrganizationRequest;
import com.vntana.core.model.token.auth.request.TokenAuthenticationRequest;
import com.vntana.core.model.token.auth.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 11:41 AM
 */
@FeignClient(name = "coreAuthenticationTokens", primary = false, path = "auth-tokens", url = "${ms.core.url}")
public interface TokenAuthenticationResourceClient {

    @PostMapping("persist")
    ResponseEntity<PersistTokenAuthenticationResultResponse> persist(@RequestBody final TokenAuthenticationPersistRequest request);

    @PostMapping("persist-with-organization")
    ResponseEntity<PersistTokenAuthenticationResultResponse> persistWithOrganization(@RequestBody final TokenAuthenticationPersistWithOrganizationRequest request);

    @PostMapping("persist-with-client-organization")
    ResponseEntity<PersistTokenAuthenticationResultResponse> persistWithClientOrganization(@RequestBody final TokenAuthenticationPersistWithClientOrganizationRequest request);

    @GetMapping("expiration/{token}")
    ResponseEntity<IsExpiredTokenAuthenticationResultResponse> isExpired(@PathVariable("token") final String token);

    @DeleteMapping("expiration/users/{userUuid}")
    ResponseEntity<ExpireTokenAuthenticationByUserResultResponse> expireByUser(@PathVariable("userUuid") final String userUuid);

    @DeleteMapping("expiration/{token}")
    ResponseEntity<ExpireTokenAuthenticationResultResponse> expire(@PathVariable("token") final String token);

    @PostMapping("tokens")
    ResponseEntity<FindTokenAuthenticationByTokenResponse> findByToken(@RequestBody final TokenAuthenticationRequest request);
}
