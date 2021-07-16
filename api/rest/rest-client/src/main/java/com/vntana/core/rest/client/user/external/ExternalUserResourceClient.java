package com.vntana.core.rest.client.user.external;

import com.vntana.core.model.user.external.request.GetOrCreateExternalUserRequest;
import com.vntana.core.model.user.external.responce.GetOrCreateExternalUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Diana Gevorgyan
 * Date: 7/16/2021
 * Time: 2:48 PM
 */
@FeignClient(name = "coreExternalUser", path = "external-user", url = "${ms.core.url}", primary = false)
public interface ExternalUserResourceClient {
    
    @PostMapping(path = "/get-external-user")
    ResponseEntity<GetOrCreateExternalUserResponse> getOrCreateExternalUser(@RequestBody final GetOrCreateExternalUserRequest request);
}
