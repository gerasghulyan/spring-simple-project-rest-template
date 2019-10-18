package com.vntana.core.rest.client.user;

import com.vntana.core.model.user.request.UserCreateRequest;
import com.vntana.core.model.user.response.UserCreateResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 3:46 PM
 */
@FeignClient(name = "coreUsers", path = "users", url = "${ms.core.url}")
public interface UserResourceClient {
    @PostMapping(path = "/create")
    UserCreateResultResponse createUser(@RequestBody final UserCreateRequest request);
}
