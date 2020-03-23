package com.vntana.core.rest.client.token.auth;

import com.vntana.core.model.token.auth.request.AuthTokenPersistRequest;
import com.vntana.core.model.token.auth.response.AuthTokenExpireByUserResultResponse;
import com.vntana.core.model.token.auth.response.AuthTokenExpireResultResponse;
import com.vntana.core.model.token.auth.response.AuthTokenIsExpiredResultResponse;
import com.vntana.core.model.token.auth.response.AuthTokenPersistResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 11:41 AM
 */
@FeignClient(name = "coreAuthTokens", primary = false, path = "auth-tokens", url = "${ms.core.url}")
public interface AuthTokenResourceClient {

    @PostMapping("persist")
    ResponseEntity<AuthTokenPersistResultResponse> persist(@RequestBody final AuthTokenPersistRequest request);

    @GetMapping("expiration/{token}")
    ResponseEntity<AuthTokenIsExpiredResultResponse> isExpired(@PathVariable("token") final String token);

    @DeleteMapping("expiration/users/{userUuid}")
    ResponseEntity<AuthTokenExpireByUserResultResponse> expireByUser(@PathVariable("userUuid") final String userUuid);

    @DeleteMapping("expiration/{token}")
    ResponseEntity<AuthTokenExpireResultResponse> expire(@PathVariable("token") final String token);
}
